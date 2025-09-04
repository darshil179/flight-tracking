package com.example.flighttracking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // Use @ManyToOne to establish a relationship with the City entity
    // This is a much better approach than storing the city as a simple String
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_location_id")
    private City fromLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_location_id")
    private City toLocation;

    @OneToMany(mappedBy = "flightRoute", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Points> path;

    // JPA requires a no-argument constructor.
    public FlightRoute() {}

    // A new constructor that accepts City objects, which the AviationStackService uses.
    public FlightRoute(String code, City fromLocation, City toLocation, List<Points> path) {
        this.code = code;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.path = path;
    }

    // A constructor for backwards compatibility with the controller.
    public FlightRoute(String code, String from, String to, List<Points> path) {
        this.code = code;
        // This is a placeholder; you'll need to fetch/create the City objects later.
        this.fromLocation = new City(from, null);
        this.toLocation = new City(to, null);
        this.path = path;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public City getFromLocation() { return fromLocation; }
    public void setFromLocation(City fromLocation) { this.fromLocation = fromLocation; }
    public City getToLocation() { return toLocation; }
    public void setToLocation(City toLocation) { this.toLocation = toLocation; }
    public List<Points> getPath() { return path; }
    public void setPath(List<Points> path) { this.path = path; }
}
