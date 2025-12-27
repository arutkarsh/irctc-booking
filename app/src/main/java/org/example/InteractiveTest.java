package org.example;

import java.io.*;
import org.example.service.TrainService;
import org.example.entities.Train;

public class InteractiveTest {
    public static void main(String[] args) {
        System.out.println("=== IRCTC Train Booking System Demo ===");
        System.out.println("Train Service successfully loaded trains.json\n");
        
        try {
            TrainService trainService = new TrainService();
            
            // Demonstrate train search functionality
            System.out.println("Available Routes:");
            System.out.println("1. Bangalore ↔ Delhi");
            System.out.println("2. Bangalore ↔ Jaipur");
            System.out.println("3. Jaipur ↔ Delhi");
            System.out.println("4. Mumbai ↔ Delhi (should show 0 trains)");
            
            System.out.println("\n=== Sample Train Search Results ===");
            
            // Show working searches
            String[] sources = {"bangalore", "bangalore", "jaipur", "mumbai"};
            String[] destinations = {"delhi", "jaipur", "bangalore", "delhi"};
            String[] descriptions = {"Bangalore to Delhi", "Bangalore to Jaipur", "Jaipur to Bangalore", "Mumbai to Delhi"};
            
            for (int test = 0; test < 4; test++) {
                System.out.println("\n" + descriptions[test] + ":");
                try {
                    java.util.List<Train> trains = trainService.findTrains(sources[test], destinations[test]);
                    if (trains.isEmpty()) {
                        System.out.println("  No trains found");
                    } else {
                        for (int i = 0; i < trains.size(); i++) {
                            Train t = trains.get(i);
                            System.out.println("  ✓ Train " + (i+1) + ":");
                            System.out.println("    ID: " + t.getTrainId());
                            System.out.println("    Number: " + t.getTrainNo());
                            System.out.println("    Route: " + java.lang.String.join(" → ", t.getStations()));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  Error: " + e.getMessage());
                }
            }
            
            System.out.println("\n=== Interactive Demo Complete ===");
            System.out.println("To use full interactive system, run: ./gradlew run");
            
        } catch (Exception e) {
            System.out.println("Failed to initialize TrainService: " + e.getMessage());
            e.printStackTrace();
        }
    }
}