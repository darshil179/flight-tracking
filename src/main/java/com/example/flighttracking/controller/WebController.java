package com.example.flighttracking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A dedicated controller for serving web pages.
 * This class handles requests for the application's root URL and returns the
 * name of the HTML template to be rendered.
 */
@Controller
public class WebController {

    /**
     * Maps the root URL ("/") to the main flight tracker page.
     * Spring will resolve the returned string "flight_tracker" to the
     * "flight_tracker.html" file found in the src/main/resources/templates/ directory.
     *
     * @return The name of the HTML template.
     */
    @GetMapping("/")
    public String showHomePage() {
        return "flight_tracker";
    }
}
