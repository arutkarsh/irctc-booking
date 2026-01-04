package org.example.service;

import org.example.entities.User;
import org.example.entities.Train;
import org.example.entities.Ticket;
import org.example.util.UserServiceUtil;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


public class UserBookingService {

    private User user;

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();
    
    private final TrainService trainService;
    private final List<Ticket> bookedTickets = new ArrayList<>();


    private static final String USERS_PATH = System.getProperty("user.dir") + "/app/src/main/java/org/example/localDb/users.json";

    public UserBookingService() throws IOException
    {
        this.trainService = new TrainService();
        loadUsers();
    }

    public UserBookingService(User user1) throws IOException {
        this.user=user1;
        this.trainService = new TrainService();
        loadUsers();
    }

    public  void loadUsers() throws IOException
    {
            File users = new File(USERS_PATH);
        this.userList=objectMapper.readValue(users,new TypeReference<List<User>>(){});   
    }

    public Boolean loginUser()
    {

        Optional<User> foundUser= userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword());
        }).findFirst();

        return foundUser.isPresent();
    }

    public Boolean signUp(User user1)
    {
        try{
            userList.add(user1);
            saveUserListToFile();
            return true;
        }
        catch (IOException ex){
            return false;
        }

    }

    public void saveUserListToFile()throws IOException{
        File userFile=new File (USERS_PATH);
        objectMapper.writeValue(userFile,userList);
    }
    public void fetchBooking()
    {
        user.printTickets();
    }
    
    public void addTicketToUser(Ticket ticket) throws IOException
    {
        user.getTicketsbooked().add(ticket);
        
        // Update user in the userList
        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getName().equalsIgnoreCase(user.getName())) {
                userList.get(i).getTicketsbooked().add(ticket);
                break;
            }
        }
        
        saveUserListToFile();
    }
    public Boolean cancelBooking(String ticketId)
    {
         if(ticketId==null || ticketId.isEmpty())
         {
             System.out.println("Ticket Id cannot be null or Empty");
             return Boolean.FALSE;
         }
         String finalTicketId1=ticketId;
         boolean removed=user.getTicketsbooked().removeIf(ticket->ticket.getTicketId().equals(finalTicketId1));

         if(removed)
         {
             System.out.println("Tickets with ticketId "+ticketId+" has been removed");
             // Update user in the userList
             for(int i = 0; i < userList.size(); i++) {
                 if(userList.get(i).getName().equalsIgnoreCase(user.getName())) {
                     userList.get(i).getTicketsbooked().removeIf(ticket->ticket.getTicketId().equals(finalTicketId1));
                     break;
                 }
             }
             try {
                 saveUserListToFile();
             } catch (IOException e) {
                 System.out.println("Error saving updated bookings: " + e.getMessage());
             }
             return true;
         }
         else{
             System.out.println("No ticket found with "+ticketId);
             return false;
         }
    }


    public List<Train> getTrains(String source, String destination) throws IOException {
        return trainService.findTrains(source, destination);
    }
    
    // Book seats - merged from BookingService
    public Ticket bookTicket(
            String trainId,
            String source,
            String destination,
            int seatsRequired) {

        Train train = findTrainById(trainId);

        if (train == null)
            throw new RuntimeException("Train not found");

        if (!hasEnoughSeats(train, seatsRequired))
            throw new RuntimeException("Enough seats are not available");

        reserveSeats(train, seatsRequired);

        Ticket ticket = new Ticket(trainId, null, source, destination, null, null);
        bookedTickets.add(ticket);

        return ticket;
    }

    // ================= Helper Methods from BookingService =================

    private Train findTrainById(String trainId) {
        for (Train train : trainService.loadTrains()) {
            if (train.getTrainId().equals(trainId))
                return train;
        }
        return null;
    }

    private boolean hasEnoughSeats(Train train, int seatsRequired) {
        int count = 0;
        for (List<Integer> row : train.getSeats()) {
            for (Integer seat : row) {
                if (seat == 1) count++;
                if (count >= seatsRequired) return true;
            }
        }
        return false;
    }

    private void reserveSeats(Train train, int seatsRequired) {
        for (List<Integer> row : train.getSeats()) {
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i) == 1) {
                    row.set(i, 0);
                    seatsRequired--;
                }
                if (seatsRequired == 0) return;
            }
        }
    }
    
    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }
}