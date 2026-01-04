package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.units.qual.t;
import org.example.entities.Train;

public class TrainService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Train> catchedTrains;

    public List<Train> loadTrains(){
        if(catchedTrains!=null)
            return catchedTrains;
    
    try{
        String trainsPath = "app/src/main/java/org/example/localDb/trains.json";
        File trainsFile = new File(trainsPath);
        catchedTrains=objectMapper.readValue(trainsFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Train.class));
    }
    catch(Exception e)
    {
        throw new RuntimeException("Failed to load trains",e);
    }
return catchedTrains;

        
    }
    //Finding trains between source and destination
    public List<Train> findTrains(String source , String destination)
    {
        List<Train> result=new ArrayList<>();
        source =source.toLowerCase();
        destination=destination.toLowerCase();

        for(Train train: loadTrains())
        {
            int srcIndex=getStationIndex(train,source);
            int desIndex=getStationIndex(train,destination);

            if(srcIndex !=-1 && desIndex !=-1 && srcIndex<desIndex)
            {
                result.add(train);
            }
            
        }
        return result;

    }
    // Filtering by seat availability

    public List<Train> findTrainsWithAvailableSeats(String source,String destination){
        List<Train> validTrains=findTrains(source, destination);
        List<Train> result=new ArrayList<>();

        for(Train train:validTrains)
        {
            if(hasAvailableSeat(train)){
                result.add(train);
            }
        }
        return result;
    }
    // Filtering by time window

    public List<Train> findTrainsByTime(
        String source,
        String destination,
        LocalTime startTime,
        LocalTime endTime){
            
            List<Train> validTrains=findTrains(source, destination);
            List<Train> result=new ArrayList<>();

            for(Train train : validTrains)
            {
                Map<String,String> times=train.getStationTimes();
                if(times == null)
                     continue;

                LocalTime srcTime=LocalTime.parse(times.get(source));
                LocalTime desTime=LocalTime.parse(times.get(destination));

                if(!srcTime.isBefore(startTime) && !desTime.isAfter(endTime))
                    result.add(train);
            }
            return result;

    } 

    // Combined filter source -> destination + seat + time

    public List<Train> searchTrains(
        String source,
            String destination,
            LocalTime startTime,
            LocalTime endTime
    ){
        List <Train> result = new ArrayList<>();
        for(Train train: findTrains(source, destination))
        {
            if(hasAvailableSeat(train) && isValidTime(train,source, destination, startTime, endTime))
                result.add(train);
        }
        return result;
    }

    //  All the methods initialized in this

    private int getStationIndex(Train train,String station){

        List<String > stations=train.getStations();

        for(int i=0;i<stations.size();i++)
        {
            if(stations.get(i).equalsIgnoreCase(station))
            {
                return i;
            }
        }
        return -1;

    }
    private boolean hasAvailableSeat(Train train)
    {
        if(train.getSeats()==null)
             return false;
        for(List<Integer> row : train.getSeats())
        {
            for(Integer seat: row)
                 if(seat==1)
                     return true;
        }
        return false;
    }
    private boolean isValidTime(
            Train train,
            String source,
            String destination,
            LocalTime startTime,
            LocalTime endTime
    ){
        Map<String, String> times = train.getStationTimes();
        if (times == null) return false;

        LocalTime src = LocalTime.parse(times.get(source));
        LocalTime dest = LocalTime.parse(times.get(destination));

        return !src.isBefore(startTime) && !dest.isAfter(endTime);
    }
    private List<Train> getAllTrains(){
        return loadTrains();
    }
    }

