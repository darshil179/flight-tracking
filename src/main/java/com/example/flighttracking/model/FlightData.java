package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A nested object within the AeroDataBox response,
 * containing departure and arrival airport information.
 */
public class FlightData {
    private final Airport departure;
    private final Airport arrival;

    public FlightData(@JsonProperty("departure") Airport departure,
                      @JsonProperty("arrival") Airport arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airport getArrival() {
        return arrival;
    }
}
