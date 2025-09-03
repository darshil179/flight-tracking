package com.example.flighttracking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

// This annotation marks the class as a JPA entity,
// mapping it to a table in the database.
@Entity
public class City {

    // The @Id annotation marks the primary key for the entity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The @Column annotation specifies the mapped column name.
    // The name in the database is "airport_code", but the camelCase
    // field name in the Java class is `airportCode`. Spring Data
    // JPA will automatically map these.
    @Column(name = "airport_code")
    private String airportCode;

    private String name;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
