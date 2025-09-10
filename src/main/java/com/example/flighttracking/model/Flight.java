package com.example.flighttracking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "flight_number", nullable = false)
    private String flightNumber;
    
    @Column(name = "airline")
    private String airline;
    
    @Column(name = "departure_airport")
    private String departureAirport;
    
    @Column(name = "arrival_airport")
    private String arrivalAirport;
    
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    
    @Column(name = "current_latitude")
    private Double currentLatitude;
    
    @Column(name = "current_longitude")
    private Double currentLongitude;
    
    @Column(name = "altitude")
    private Double altitude;
    
    @Column(name = "speed")
    private Double speed;
    
    @Column(name = "status")
    private String status; // scheduled, active, landed, cancelled
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Flight() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Flight(String flightNumber) {
        this();
        this.flightNumber = flightNumber;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    
    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }
    
    public String getDepartureAirport() { return departureAirport; }
    public void setDepartureAirport(String departureAirport) { this.departureAirport = departureAirport; }
    
    public String getArrivalAirport() { return arrivalAirport; }
    public void setArrivalAirport(String arrivalAirport) { this.arrivalAirport = arrivalAirport; }
    
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    
    public Double getCurrentLatitude() { return currentLatitude; }
    public void setCurrentLatitude(Double currentLatitude) { 
        this.currentLatitude = currentLatitude;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Double getCurrentLongitude() { return currentLongitude; }
    public void setCurrentLongitude(Double currentLongitude) { 
        this.currentLongitude = currentLongitude;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Double getAltitude() { return altitude; }
    public void setAltitude(Double altitude) { this.altitude = altitude; }
    
    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}