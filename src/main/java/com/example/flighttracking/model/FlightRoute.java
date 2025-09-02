package com.example.flighttracking.model;

import java.util.List;

public class FlightRoute {
    private final String from;
    private final String to;
    private final List<Points> path;

    public FlightRoute(String from, String to, List<Points> path) {
        this.from = from;
        this.to = to;
        this.path = path;
    }

    // Getters
    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<Points> getPath() {
        return path;
    }
}
