package com.example.flighttracking.repository;

import com.example.flighttracking.model.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The @Repository annotation marks this interface as a Spring Data repository.
// JpaRepository<T, ID> provides standard CRUD (Create, Read, Update, Delete) operations.
// T is the entity type (FlightRoute) and ID is the type of the primary key (Long).
@Repository
public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {

    /**
     * Finds a FlightRoute by its unique flight code.
     * Spring Data JPA automatically generates the query for this method based on the name.
     *
     * @param code The unique flight code (e.g., "UA123").
     * @return An Optional containing the found FlightRoute, or an empty Optional if not found.
     */
    Optional<FlightRoute> findByCode(String code);
}
