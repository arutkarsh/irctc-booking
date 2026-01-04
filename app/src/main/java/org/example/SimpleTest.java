package org.example;

import org.example.service.UserBookingService;
import org.example.entities.User;
import org.example.entities.Train;
import org.example.util.UserServiceUtil;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class SimpleTest {
    public static void main(String[] args) {
        try {
            // Create a dummy user for testing
            User dummyUser = new User("test", "test", UserServiceUtil.hashPassword("test"), new ArrayList<>(), UUID.randomUUID().toString());
            UserBookingService service = new UserBookingService(dummyUser);
            
            System.out.println("Testing train search...");
            List<Train> trains = service.getTrains("jaipur", "delhi");
            System.out.println("Found " + trains.size() + " trains");
            
            for(Train t : trains) {
                System.out.println("Train ID: " + t.getTrainId());
                System.out.println("Train Info: " + t.getTrainInfo());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}