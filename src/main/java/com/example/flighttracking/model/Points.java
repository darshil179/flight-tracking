package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// This annotation marks the class as a JPA entity,
// mapping it to a table in the database.
@Entity
public class Points {

    // The @Id annotation marks the primary key for the entity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The latitude and longitude of the point.
    private double lat;
    private double lng;

    // This annotation defines a many-to-one relationship.
    // Many Points can belong to one FlightRoute.
    @ManyToOne
    @JoinColumn(name = "flight_route_id") // Specifies the foreign key column.
    @JsonBackReference // Prevents infinite recursion during JSON serialization.
    private FlightRoute flightRoute;

    // JPA requires a no-argument constructor.
    public Points() {}

    public Points(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public FlightRoute getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(FlightRoute flightRoute) {
        this.flightRoute = flightRoute;
    }
}
