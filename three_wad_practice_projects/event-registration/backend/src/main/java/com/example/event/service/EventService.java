package com.example.event.service;
import com.example.event.model.*;
import com.example.event.repository.EventRepository;
import com.example.event.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public EventService(EventRepository eventRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public Event addEvent(Event event) {
        if (event.getCapacity() <= 0 || event.getAvailableSeats() < 0 || event.getAvailableSeats() > event.getCapacity()) {
            throw new IllegalArgumentException("Capacity must be positive and available seats must be between zero and capacity");
        }
        if (event.getAvailabilityStatus() == null || event.getAvailabilityStatus().isBlank()) {
            event.setAvailabilityStatus(event.getAvailableSeats() > 0 ? "AVAILABLE" : "FULL");
        }
        return eventRepository.save(event);
    }

    public List<Event> getEvents() { return eventRepository.findAll(); }

    public List<EventType> getTypes() { return Arrays.asList(EventType.values()); }

    public Registration addRegistration(Registration registration) {
        if (registration.getSeatsRequested() <= 0) throw new IllegalArgumentException("seatsRequested must be greater than zero");
        Event event = eventRepository.findFirstByEventType(registration.getEventType()).orElseThrow(() -> new IllegalArgumentException("Event type not found"));
        if (event.getAvailableSeats() < registration.getSeatsRequested()) throw new IllegalArgumentException("Not enough seats available");
        event.setAvailableSeats(event.getAvailableSeats() - registration.getSeatsRequested());
        event.setAvailabilityStatus(event.getAvailableSeats() == 0 ? "FULL" : "AVAILABLE");
        eventRepository.save(event);
        try { return registrationRepository.save(registration); } catch (RuntimeException ex) {
            event.setAvailableSeats(event.getAvailableSeats() + registration.getSeatsRequested());
            event.setAvailabilityStatus("AVAILABLE"); eventRepository.save(event); throw ex;
        }
    }

    public List<Registration> getRegistrations() { return registrationRepository.findAll(); }

    public List<RegistrationCount> getCounts() { return registrationRepository.countRegistrationsByEventType(); }
}
