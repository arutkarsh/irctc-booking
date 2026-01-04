package org.example;

import org.example.service.TrainService;
import org.example.entities.Train;
import java.util.List;
public class TrainTest {
    public static void main(String[] args) {
        TrainService trainService = new TrainService();
        
        System.out.println("=== Testing Train Search Functionality ===");
        
        // Test 1: Bangalore to Delhi
        System.out.println("\n1. Searching trains from Bangalore to Delhi:");
        List<Train> trains1 = trainService.findTrains("bangalore", "delhi");
        for (int i = 0; i < trains1.size(); i++) {
            Train t = trains1.get(i);
            System.out.println("  Train " + (i+1) + " - ID: " + t.getTrainId() + ", No: " + t.getTrainNo());
            System.out.println("    Stations: " + t.getStations());
        }
        
        // Test 2: Invalid route
        System.out.println("\n2. Searching trains from Mumbai to Delhi:");
        List<Train> trains2 = trainService.findTrains("mumbai", "delhi");
        System.out.println("  Found " + trains2.size() + " trains");
        
        // Test 3: Jaipur to Delhi
        System.out.println("\n3. Searching trains from Jaipur to Delhi:");
        List<Train> trains3 = trainService.findTrains("jaipur", "delhi");
        for (int i = 0; i < trains3.size(); i++) {
            Train t = trains3.get(i);
            System.out.println("  Train " + (i+1) + " - ID: " + t.getTrainId() + ", No: " + t.getTrainNo());
        }
        
        // Test 4: Jaipur to Bangalore
        System.out.println("\n4. Searching trains from Jaipur to Bangalore:");
        List<Train> trains4 = trainService.findTrains("jaipur", "bangalore");
        for (int i = 0; i < trains4.size(); i++) {
            Train t = trains4.get(i);
            System.out.println("  Train " + (i+1) + " - ID: " + t.getTrainId() + ", No: " + t.getTrainNo());
        }
        
        System.out.println("\n=== Test Complete ===");
    }
}