package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "flight_routes")
public class FlightRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String fromLocation;
    private String toLocation;

    @OneToMany(mappedBy = "flightRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Points> path;

    // JPA requires a no-argument constructor.
    public FlightRoute() {}

    // Main constructor for JPA and full object creation.
    public FlightRoute(String code, String fromLocation, String toLocation, List<Points> path) {
        this.code = code;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.path = path;
    }

    // Overloaded constructor for use in controllers and other places
    // where the flight code is not needed at creation.
    public FlightRoute(String fromLocation, String toLocation, List<Points> path) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.path = path;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getFromLocation() { return fromLocation; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }
    public String getToLocation() { return toLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }
    public List<Points> getPath() { return path; }
    public void setPath(List<Points> path) { this.path = path; }
}
