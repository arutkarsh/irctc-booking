package org.example.service;

import org.example.entities.User;
import org.example.entities.Train;
import org.example.entities.Ticket;
import org.example.util.UserServiceUtil;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


public class UserBookingService {

    private User user;

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();


    private static final String USERS_PATH = "src/main/java/org/example/localDb/users.json";

    public UserBookingService() throws IOException
    {
        loadUsers();
    }

    public UserBookingService(User user1) throws IOException {
        this.user=user1;
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
    public Boolean cancelBooking(String ticketId)
    {
           //TODO complete this function
         Scanner sc=new Scanner(System.in);
         System.out.println("Enter the Ticket Id to Cancel");
         ticketId=sc.next();

         if(ticketId==null || ticketId.isEmpty())
         {
             System.out.println("Ticket Id cannot be null or Empty");
             return Boolean.FALSE;
         }
         String finalTicketId1=ticketId;
         boolean removed=user.getTicketsbooked().removeIf(ticket->ticket.getTicketId().equals(finalTicketId1));

         if(removed)
         {
             System.out.println("Tickets with ticketId"+ticketId+"has been removed");
             return true;
         }
         else{
             System.out.println("No ticket with found "+ticketId);
             return false;
         }
    }


    public List<Train> getTrains(String source, String destination) throws IOException {
        TrainService trainService = new TrainService();
        return trainService.findTrains(source, destination);
    }
}