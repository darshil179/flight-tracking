package com.example.flighttracking.controller;

import com.example.flighttracking.model.FlightRoute;
import com.example.flighttracking.service.AviationStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// The @RestController annotation combines @Controller and @ResponseBody,
// indicating that this class handles web requests and returns data directly.
@RestController
public class FlightRouteController {

    private final AviationStackService aviationStackService;

    // Use constructor injection to provide the AviationStackService.
    @Autowired
    public FlightRouteController(AviationStackService aviationStackService) {
        this.aviationStackService = aviationStackService;
    }

    /**
     * Handles GET requests to the /flight-route endpoint.
     * It accepts an optional 'flightNumber' request parameter.
     * It now calls the AviationStackService to fetch live data.
     * @param flightNumber The flight number to look up (e.g., "AI101").
     * @return A ResponseEntity with the FlightRoute object if found, or a 404 Not Found status.
     */
    @GetMapping("/flight-route")
    public ResponseEntity<FlightRoute> getFlightRoute(@RequestParam String flightNumber) {
        // Call the service to get the flight route.
        Optional<FlightRoute> route = aviationStackService.fetchAndSaveFlightRoute(flightNumber);

        // Check if the optional contains a flight route and return it.
        return route.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
