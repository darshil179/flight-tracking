package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single geographical point on the flight route.
 */
public class RoutePoint {
    private final double latitude;
    private final double longitude;

    public RoutePoint(@JsonProperty("latitude") double latitude,
                      @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
