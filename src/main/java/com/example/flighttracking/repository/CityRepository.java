package com.example.flighttracking.repository;

import com.example.flighttracking.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository provides standard CRUD operations for the City entity.
// The key here is the method name: `findByAirportCode`.
// Spring Data JPA's query creation from method names works by
// finding a property that matches the name. Since we changed
// our City class to have a `airportCode` property, this method
public interface CityRepository extends JpaRepository<City, Long> {
    
    // Corrected method name to match the 'airportCode' field in the City entity.
    Optional<City> findByAirportCode(String airportCode);
}
