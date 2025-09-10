package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class to represent a simple airport with its name and ICAO code.
 */
public class Airport {
    private final String name;
    private final String icao;

    public Airport(@JsonProperty("name") String name,
                   @JsonProperty("icao") String icao) {
        this.name = name;
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public String getIcao() {
        return icao;
    }
}
