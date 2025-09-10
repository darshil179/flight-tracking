package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the top-level JSON response from the AeroDataBox API's flight tracker endpoint.
 */
public class AeroDataBoxResponse {
    private final FlightData flight;
    private final List<RoutePoint> route;

    public AeroDataBoxResponse(@JsonProperty("flight") FlightData flight,
                               @JsonProperty("route") List<RoutePoint> route) {
        this.flight = flight;
        this.route = route;
    }

    public FlightData getFlight() {
        return flight;
    }

    public List<RoutePoint> getRoute() {
        return route;
    }
}
