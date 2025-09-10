package com.example.flighttracking.service;

import com.example.flighttracking.model.Flight;
import com.example.flighttracking.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    // OpenSky Network API doesn't require API key
    private static final String OPENSKY_API_URL = "https://opensky-network.org/api";
    
    public Flight addFlightToTrack(String callsign) throws Exception {
        // Check if flight already exists
        if (flightRepository.existsByFlightNumber(callsign)) {
            throw new Exception("Flight already being tracked");
        }
        
        // Fetch flight data from API
        Map<String, Object> flightData = fetchFlightData(callsign);
        
        // Create new flight entity
        Flight flight = new Flight(callsign);
        populateFlightFromAPI(flight, flightData);
        
        return flightRepository.save(flight);
    }
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public List<Flight> getActiveFlights() {
        return flightRepository.findByStatus("active");
    }
    
    public Flight getFlightByNumber(String flightNumber) throws Exception {
        return flightRepository.findByFlightNumber(flightNumber)
            .orElseThrow(() -> new Exception("Flight not found"));
    }
    
    public Flight updateFlightPosition(Long id) throws Exception {
        Flight flight = flightRepository.findById(id)
            .orElseThrow(() -> new Exception("Flight not found"));
        
        // Fetch latest position from API
        Map<String, Object> flightData = fetchFlightData(flight.getFlightNumber());
        populateFlightFromAPI(flight, flightData);
        
        return flightRepository.save(flight);
    }
    
    public void removeFlight(Long id) throws Exception {
        if (!flightRepository.existsById(id)) {
            throw new Exception("Flight not found");
        }
        flightRepository.deleteById(id);
    }
    
    public List<Flight> getAllCurrentFlights() {
        // Get all current flights from OpenSky and convert to our Flight objects
        try {
            String url = OPENSKY_API_URL + "/states/all";
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "FlightTracker/1.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Map.class);
            
            Map<String, Object> data = response.getBody();
            List<List<Object>> states = (List<List<Object>>) data.get("states");
            
            List<Flight> currentFlights = new ArrayList<>();
            
            if (states != null) {
                for (List<Object> state : states) {
                    if (state.size() > 6 && state.get(1) != null && state.get(5) != null && state.get(6) != null) {
                        String callsign = state.get(1).toString().trim();
                        if (!callsign.isEmpty()) {
                            Flight flight = new Flight(callsign);
                            populateFlightFromAPI(flight, convertOpenSkyState(state));
                            currentFlights.add(flight);
                        }
                    }
                }
            }
            
            return currentFlights;
            
        } catch (Exception e) {
            System.err.println("Error fetching all current flights: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<Object> getFlightRoute(String flightNumber) throws Exception {
        // For OpenSky, we can only get current position, not historical route
        // This would require storing position history in database
        Flight flight = getFlightByNumber(flightNumber);
        if (flight.getCurrentLatitude() != null && flight.getCurrentLongitude() != null) {
            return List.of(Map.of(
                "lat", flight.getCurrentLatitude(),
                "lng", flight.getCurrentLongitude(),
                "timestamp", flight.getUpdatedAt()
            ));
        }
        return new ArrayList<>();
    }
    
    private Map<String, Object> fetchFlightData(String callsign) {
        try {
            // OpenSky Network API - get all current states, then filter by callsign
            String url = OPENSKY_API_URL + "/states/all";
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "FlightTracker/1.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Map.class);
            
            Map<String, Object> data = response.getBody();
            List<List<Object>> states = (List<List<Object>>) data.get("states");
            
            if (states != null) {
                // Find flight by callsign (case insensitive)
                for (List<Object> state : states) {
                    if (state.size() > 1 && state.get(1) != null) {
                        String stateCallsign = state.get(1).toString().trim();
                        if (stateCallsign.equalsIgnoreCase(callsign)) {
                            return convertOpenSkyState(state);
                        }
                    }
                }
            }
            
            throw new RuntimeException("Flight not found in current active flights");
            
        } catch (Exception e) {
            throw new RuntimeException("Error fetching flight data: " + e.getMessage());
        }
    }
    
    private Map<String, Object> convertOpenSkyState(List<Object> state) {
        // OpenSky state vector format:
        // [0] icao24, [1] callsign, [2] origin_country, [3] time_position, [4] last_contact,
        // [5] longitude, [6] latitude, [7] baro_altitude, [8] on_ground, [9] velocity,
        // [10] true_track, [11] vertical_rate, [12] sensors, [13] geo_altitude, [14] squawk, [15] spi, [16] position_source
        
        Map<String, Object> flightData = new HashMap<>();
        
        if (state.size() > 16) {
            flightData.put("icao24", state.get(0));
            flightData.put("callsign", state.get(1));
            flightData.put("origin_country", state.get(2));
            flightData.put("longitude", state.get(5));
            flightData.put("latitude", state.get(6));
            flightData.put("altitude", state.get(7));
            flightData.put("on_ground", state.get(8));
            flightData.put("velocity", state.get(9));
            flightData.put("true_track", state.get(10));
            flightData.put("vertical_rate", state.get(11));
            flightData.put("last_contact", state.get(4));
        }
        
        return flightData;
    }
    
    private void populateFlightFromAPI(Flight flight, Map<String, Object> apiData) {
        try {
            // Parse OpenSky API response
            flight.setAirline((String) apiData.get("origin_country")); // Store country as airline for now
            
            // Set coordinates
            Object longitude = apiData.get("longitude");
            Object latitude = apiData.get("latitude");
            Object altitude = apiData.get("altitude");
            Object velocity = apiData.get("velocity");
            
            if (longitude != null && !longitude.toString().equals("null")) {
                flight.setCurrentLongitude(((Number) longitude).doubleValue());
            }
            if (latitude != null && !latitude.toString().equals("null")) {
                flight.setCurrentLatitude(((Number) latitude).doubleValue());
            }
            if (altitude != null && !altitude.toString().equals("null")) {
                flight.setAltitude(((Number) altitude).doubleValue());
            }
            if (velocity != null && !velocity.toString().equals("null")) {
                // Convert m/s to km/h
                double speedKmh = ((Number) velocity).doubleValue() * 3.6;
                flight.setSpeed(speedKmh);
            }
            
            // Determine status based on on_ground flag
            Boolean onGround = (Boolean) apiData.get("on_ground");
            if (onGround != null) {
                flight.setStatus(onGround ? "landed" : "active");
            } else {
                flight.setStatus("active");
            }
            
            // Set update time based on last_contact
            Object lastContact = apiData.get("last_contact");
            if (lastContact != null && !lastContact.toString().equals("null")) {
                long timestamp = ((Number) lastContact).longValue();
                flight.setUpdatedAt(LocalDateTime.ofEpochSecond(timestamp, 0, 
                    java.time.ZoneOffset.UTC));
            }
            
        } catch (Exception e) {
            System.err.println("Error parsing OpenSky API data: " + e.getMessage());
            e.printStackTrace();
            // Set default values if parsing fails
            if (flight.getStatus() == null) {
                flight.setStatus("unknown");
            }
        }
    }
}