package com.example.flighttracking.repository;

import com.example.flighttracking.model.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The @Repository annotation marks this interface as a data repository.
// JpaRepository<FlightRoute, Long> indicates that it's a repository for the FlightRoute entity,
// and the primary key of the FlightRoute entity is of type Long.
@Repository
public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {

   
    Optional<FlightRoute> findByCode(String code);
}
