package com.example.event.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registrations")
public class Registration {
    @Id
    private String registrationId;
    private String registrationId;
    private String participantName;
    private String participantEmail;
    private EventType eventType;
    private int seatsRequested;
    private String registrationDate;

    public Registration() {}
    public String getRegistrationId(){return registrationId;} public void setRegistrationId(String v){registrationId=v;}
public String getParticipantName(){return participantName;} public void setParticipantName(String v){participantName=v;}
public String getParticipantEmail(){return participantEmail;} public void setParticipantEmail(String v){participantEmail=v;}
public EventType getEventType(){return eventType;} public void setEventType(EventType v){eventType=v;}
public int getSeatsRequested(){return seatsRequested;} public void setSeatsRequested(int v){seatsRequested=v;}
public String getRegistrationDate(){return registrationDate;} public void setRegistrationDate(String v){registrationDate=v;}
}
