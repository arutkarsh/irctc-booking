package org.example;

import org.example.service.TrainService;
import java.util.List;
import org.example.entities.Train;

public class DebugTrain {
    public static void main(String[] args) {
        try {
            TrainService trainService = new TrainService();
            List<Train> allTrains = trainService.loadTrains();
            System.out.println("Total trains loaded: " + allTrains.size());
            
            for(Train train : allTrains) {
                System.out.println("Train ID: " + train.getTrainId());
                System.out.println("Train No: " + train.getTrainNo());
                System.out.println("Stations: " + train.getStations());
                System.out.println("Station Times: " + train.getStationTimes());
                System.out.println("---");
            }
            
            // Test search
            List<Train> results = trainService.findTrains("jaipur", "delhi");
            System.out.println("Search results for jaipur to delhi: " + results.size());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}