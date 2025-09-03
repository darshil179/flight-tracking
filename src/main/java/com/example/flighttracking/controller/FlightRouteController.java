package com.example.flighttracking.controller;

import com.example.flighttracking.model.FlightRoute;
import com.example.flighttracking.model.Points;
import com.example.flighttracking.repository.FlightRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

// The @RestController annotation combines @Controller and @ResponseBody,
// indicating that this class handles web requests and returns data directly.
@RestController
public class FlightRouteController {

    // Handles GET requests to the /flight-route endpoint.
    // It accepts an optional 'flightNumber' request parameter.
    @GetMapping("/flight-route")
    public FlightRoute getFlightRoute(@RequestParam(required = false) String flightNumber) {
        // Return different hardcoded routes based on the flight number provided.
        if ("UA123".equalsIgnoreCase(flightNumber)) {
            List<Points> path = Arrays.asList(
                new Points(23.0225, 72.5714),    // Ahmedabad
                new Points(51.5074, -0.1278),    // London
                new Points(43.6532, -79.3832)    // Toronto
            );
            return new FlightRoute("Ahmedabad", "Toronto", path);
        } else if ("DL456".equalsIgnoreCase(flightNumber)) {
            List<Points> path = Arrays.asList(
                new Points(33.7490, -84.3880),    // Atlanta
                new Points(40.7128, -74.0060),    // New York
                new Points(34.0522, -118.2437)   // Los Angeles
            );
            return new FlightRoute("Atlanta", "Los Angeles", path);
        } else {
            // Return an empty or default response if no flight number is provided or if it's not recognized.
            return new FlightRoute("", "", Arrays.asList());
        }
    }
}