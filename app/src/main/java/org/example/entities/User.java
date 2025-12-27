package org.example.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

@JsonProperty("name")
private String name;

@JsonProperty("password") 
private String password;

@JsonProperty("hashed_password")
private String hashPassword;

@JsonProperty("tickets_booked")
private List<org.example.entities.Ticket> ticketsbooked;

@JsonProperty("user_id")
private String userId;

public User() {
    // Default constructor for Jackson
}

public User(String name, String password, String hashPassword, List<Ticket> ticketsbooked, String userId) {
    this.name = name;
    this.password = password;
    this.hashPassword = hashPassword;
    this.ticketsbooked = ticketsbooked;
    this.userId = userId;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public String getHashPassword() {
    return hashPassword;
}

public void setHashPassword(String hashPassword) {
    this.hashPassword = hashPassword;
}

public List<org.example.entities.Ticket> getTicketsbooked() {
    return ticketsbooked;
}

public void setTicketsbooked(List<org.example.entities.Ticket> ticketsbooked) {
    this.ticketsbooked = ticketsbooked;
}

public String getUserId() {
    return userId;
}

public void setUserId(String userId) {
    this.userId = userId;
}

    public void printTickets()
    {
        for(int i=0;i<getTicketsbooked().size();i++)
            System.out.println(getTicketsbooked().get(i).getTicketInfo());
    }
}
