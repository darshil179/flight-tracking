package com.example.flighttracking.controller;

import com.example.flighttracking.model.Flight;
import com.example.flighttracking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*") // Allow all origins for development
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    // Add a new flight to track
    @PostMapping("/add")
    public ResponseEntity<?> addFlight(@RequestParam String flightNumber) {
        try {
            Flight flight = flightService.addFlightToTrack(flightNumber);
            return ResponseEntity.ok(flight);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error adding flight: " + e.getMessage());
        }
    }
    
    // Get all tracked flights
    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
    
    // Get all active flights (currently in air)
    @GetMapping("/active")
    public ResponseEntity<List<Flight>> getActiveFlights() {
        List<Flight> activeFlights = flightService.getActiveFlights();
        return ResponseEntity.ok(activeFlights);
    }
    
    // Get specific flight details
    @GetMapping("/{flightNumber}")
    public ResponseEntity<?> getFlightDetails(@PathVariable String flightNumber) {
        try {
            Flight flight = flightService.getFlightByNumber(flightNumber);
            return ResponseEntity.ok(flight);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Update flight position (called by scheduled task)
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateFlightPosition(@PathVariable Long id) {
        try {
            Flight updatedFlight = flightService.updateFlightPosition(id);
            return ResponseEntity.ok(updatedFlight);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error updating flight: " + e.getMessage());
        }
    }
    
    // Remove a flight from tracking
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFlight(@PathVariable Long id) {
        try {
            flightService.removeFlight(id);
            return ResponseEntity.ok("Flight removed from tracking");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error removing flight: " + e.getMessage());
        }
    }
    
    // Get all current flights from OpenSky (live data)
    @GetMapping("/live")
    public ResponseEntity<List<Flight>> getCurrentFlights() {
        List<Flight> currentFlights = flightService.getAllCurrentFlights();
        return ResponseEntity.ok(currentFlights);
    }
}