package com.example.flighttracking.model;

public class Points {

    private final double lat;
    private final double lng;

    public Points(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // Getters
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
