package com.example.flighttracking;

import com.example.flighttracking.model.FlightRoute;
import com.example.flighttracking.model.Points;
import com.example.flighttracking.repository.FlightRouteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

// The @Component annotation marks this class as a Spring-managed component.
// It will be automatically detected and its run method will be executed on startup.
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final FlightRouteRepository flightRouteRepository;

    // The repository is injected here, allowing us to save data to the database.
    public DatabaseInitializer(FlightRouteRepository flightRouteRepository) {
        this.flightRouteRepository = flightRouteRepository;
    }

    // This method is called by Spring Boot automatically after the application starts.
    @Override
    public void run(String... args) throws Exception {

        // Check if data already exists to avoid duplicates.
        if (flightRouteRepository.count() == 0) {
            System.out.println("Adding default flight data...");

            // Create a sample flight route (e.g., from New York to London)
            List<Points> route1Path = Arrays.asList(
                new Points(40.7128, -74.0060), // New York
                new Points(51.5074, -0.1278)  // London
            );
            FlightRoute route1 = new FlightRoute("BA202", "New York", "London", route1Path);
            // Link the points back to the flight route
            route1Path.forEach(point -> point.setFlightRoute(route1));

            // Create a second sample flight route (e.g., from Tokyo to Los Angeles)
            List<Points> route2Path = Arrays.asList(
                new Points(35.6895, 139.6917), // Tokyo
                new Points(34.0522, -118.2437) // Los Angeles
            );
            FlightRoute route2 = new FlightRoute("JAL001", "Tokyo", "Los Angeles", route2Path);
            route2Path.forEach(point -> point.setFlightRoute(route2));

            // Save the routes to the database. JPA will handle saving the points as well.
            flightRouteRepository.saveAll(Arrays.asList(route1, route2));

            System.out.println("Default flight data added successfully.");
        }
    }
}
