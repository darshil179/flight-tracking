package com.example.flighttracking.service;

import com.example.flighttracking.model.City;
import com.example.flighttracking.model.FlightRoute;
import com.example.flighttracking.model.Points;
import com.example.flighttracking.repository.CityRepository;
import com.example.flighttracking.repository.FlightRouteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AviationStackService {

    // Inject the repositories to save data to the database.
    private final FlightRouteRepository flightRouteRepository;
    private final CityRepository cityRepository;

    // Use @Value to inject the API key from application.properties.
    @Value("${aviationstack.api.key}")
    private String apiKey;

    public AviationStackService(FlightRouteRepository flightRouteRepository, CityRepository cityRepository) {
        this.flightRouteRepository = flightRouteRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * Fetches and saves a flight route from the AviationStack API.
     * This method will also check the database for cached data first.
     * @param flightIcaoNumber The unique flight number (e.g., "AI123").
     * @return The FlightRoute object, either from the database or the API.
     */
    public Optional<FlightRoute> fetchAndSaveFlightRoute(String flightIcaoNumber) {
        // First, check if the route is already in the database (caching).
        Optional<FlightRoute> existingRoute = flightRouteRepository.findByCode(flightIcaoNumber);
        if (existingRoute.isPresent()) {
            return existingRoute;
        }

        // Build the API URL with the flight number and API key.
        // Updated to use HTTPS for better security.
        String apiUrl = UriComponentsBuilder.fromHttpUrl("https://api.aviationstack.com/v1/flights")
                .queryParam("access_key", apiKey)
                .queryParam("flight_icao", flightIcaoNumber)
                .toUriString();
        
        // Log the API URL to see what is being called
        System.out.println("Calling API URL: " + apiUrl);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
            
            // Log the full JSON response to see what data is returned
            System.out.println("Received API response: " + jsonResponse);
            
            JsonNode root = objectMapper.readTree(jsonResponse);
            
            // Defensive programming: Check if the 'data' array exists and is not empty.
            JsonNode flightDataNode = root.path("data");
            if (!flightDataNode.isArray() || flightDataNode.size() == 0) {
                System.err.println("No flight data found for the given flight number.");
                return Optional.empty();
            }

            JsonNode flightData = flightDataNode.get(0);

            // Extract relevant data from the JSON response.
            String flightCode = flightData.path("flight").path("icao_code").asText();
            String departureIcao = flightData.path("departure").path("icao").asText();
            String arrivalIcao = flightData.path("arrival").path("icao").asText();
            String departureCityName = flightData.path("departure").path("airport").asText();
            String arrivalCityName = flightData.path("arrival").path("airport").asText();

            // Create or get the City objects for the route.
            City departureCity = getOrCreateCity(departureCityName, departureIcao);
            City arrivalCity = getOrCreateCity(arrivalCityName, arrivalIcao);

            // Create the path (we'll just use the start and end points for now).
            List<Points> path = List.of(
                    new Points(
                            flightData.path("departure").path("latitude").asDouble(),
                            flightData.path("departure").path("longitude").asDouble()
                    ),
                    new Points(
                            flightData.path("arrival").path("latitude").asDouble(),
                            flightData.path("arrival").path("longitude").asDouble()
                    )
            );

            // Create and save the new FlightRoute.
            FlightRoute newRoute = new FlightRoute(flightCode, departureCity, arrivalCity, path);
            return Optional.of(flightRouteRepository.save(newRoute));

        } catch (Exception e) {
            System.err.println("Error fetching or parsing flight data: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * A helper method to save or retrieve a city from the database based on its airport code.
     * This avoids creating duplicate city entries.
     * @param cityName The name of the city.
     * @param airportCode The airport's ICAO code.
     * @return The City object.
     */
    private City getOrCreateCity(String cityName, String airportCode) {
        return cityRepository.findByAirportCode(airportCode)
                .orElseGet(() -> cityRepository.save(new City(cityName, airportCode)));
    }
}
