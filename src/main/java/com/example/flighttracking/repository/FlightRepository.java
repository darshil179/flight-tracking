package com.example.flighttracking.repository;

import com.example.flighttracking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    // Find flight by flight number
    Optional<Flight> findByFlightNumber(String flightNumber);
    
    // Check if flight exists by flight number
    boolean existsByFlightNumber(String flightNumber);
    
    // Find flights by status
    List<Flight> findByStatus(String status);
    
    // Find flights by airline
    List<Flight> findByAirline(String airline);
    
    // Find flights departing from specific airport
    List<Flight> findByDepartureAirport(String departureAirport);
    
    // Find flights arriving at specific airport
    List<Flight> findByArrivalAirport(String arrivalAirport);
    
    // Find active flights (in air) with current coordinates
    @Query("SELECT f FROM Flight f WHERE f.status = 'active' AND f.currentLatitude IS NOT NULL AND f.currentLongitude IS NOT NULL")
    List<Flight> findActiveFlightsWithCoordinates();
    
    // Find flights updated within last N minutes
    @Query("SELECT f FROM Flight f WHERE f.updatedAt > CURRENT_TIMESTAMP - :minutes MINUTE")
    List<Flight> findRecentlyUpdatedFlights(int minutes);
    
    // Find flights by route (departure to arrival)
    @Query("SELECT f FROM Flight f WHERE f.departureAirport = :departure AND f.arrivalAirport = :arrival")
    List<Flight> findFlightsByRoute(String departure, String arrival);
}