package com.example.event.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {
    @Id
    private String eventId;
    private String eventId;
    private String eventName;
    private EventType eventType;
    private String venue;
    private int capacity;
    private int availableSeats;
    private String availabilityStatus;

    public Event() {}
    public String getEventId(){return eventId;} public void setEventId(String v){eventId=v;}
public String getEventName(){return eventName;} public void setEventName(String v){eventName=v;}
public EventType getEventType(){return eventType;} public void setEventType(EventType v){eventType=v;}
public String getVenue(){return venue;} public void setVenue(String v){venue=v;}
public int getCapacity(){return capacity;} public void setCapacity(int v){capacity=v;}
public int getAvailableSeats(){return availableSeats;} public void setAvailableSeats(int v){availableSeats=v;}
public String getAvailabilityStatus(){return availabilityStatus;} public void setAvailabilityStatus(String v){availabilityStatus=v;}
}
