package com.example.flighttracking.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.flighttracking.model.FlightRoute;
import com.example.flighttracking.model.Points;

@Controller
public class FlightRouteController {

    @GetMapping("/flight-route")
    @ResponseBody
    public FlightRoute getFlightRoute() {
        List<Points> path = List.of(
            new Points(23.0225, 72.5714), // Ahmedabad, India
            new Points(51.5072, -0.1276), // London, UK (Stopover)
            new Points(43.6532, -79.3832)  // Toronto, Canada
        );

        return new FlightRoute("Ahmedabad", "Toronto", path);
    }

    @GetMapping("/")
    public String redirectToFrontend() {
        return "redirect:/flight_tracker.html";
    }
}
