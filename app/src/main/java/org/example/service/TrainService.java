
package org.example.service;

import org.example.entities.Train;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class TrainService {
    
    private static final String TRAINS_PATH = "src/main/java/org/example/localDb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public List<Train> findTrains(String source, String destination) throws IOException {
        List<Train> allTrains = loadTrains();
        
        if (source == null || destination == null || 
            source.trim().isEmpty() || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Source and destination cannot be null or empty");
        }
        
        String normalizedSource = source.toLowerCase().trim();
        String normalizedDestination = destination.toLowerCase().trim();
        
        return allTrains.stream()
            .filter(train -> train.getStations().contains(normalizedSource) && 
                           train.getStations().contains(normalizedDestination))
            .collect(Collectors.toList());
    }
    
    private List<Train> loadTrains() throws IOException {
        try {
            File trainsFile = new File(TRAINS_PATH);
            
            if (!trainsFile.exists()) {
                throw new IOException("Trains file not found: " + TRAINS_PATH);
            }
            
            List<Train> trains = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>() {});
            return trains != null ? trains : new ArrayList<>();
            
        } catch (IOException e) {
            throw new IOException("Failed to load trains from JSON: " + e.getMessage(), e);
        }
    }
}