package com.example.flighttracking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "flight_tracker"; // This will serve flight_tracker.html from templates folder
    }

    @GetMapping("/tracker")
    public String tracker() {
        return "flight_tracker"; // Alternative route
    }
}