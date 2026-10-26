package com.example.event;
import com.example.event.controller.*;
import com.example.event.model.*;
import com.example.event.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {EventController.class, RegistrationController.class})
class ApiControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean EventService service;

    @Test void getAllPrimaryRecords() throws Exception {
        Event b = new Event(); b.setEventId("E1"); b.setEventName("Tech Talk"); b.setEventType(EventType.TECH); b.setVenue("Hall A"); b.setCapacity(100); b.setAvailableSeats(100); b.setAvailabilityStatus("AVAILABLE");
        when(service.getEvents()).thenReturn(List.of(b));
        mvc.perform(get("/events"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].eventId").value("E1"));
    }

    @Test void addPrimaryRecord() throws Exception {
        Event b = new Event(); b.setEventId("E1"); b.setEventName("Tech Talk"); b.setEventType(EventType.TECH); b.setVenue("Hall A"); b.setCapacity(100); b.setAvailableSeats(100); b.setAvailabilityStatus("AVAILABLE");
        given(service.addEvent(any(Event.class))).willReturn(b);
        mvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content("{\"eventId\":\"E1\",\"eventName\":\"Tech Talk\",\"eventType\":\"TECH\",\"venue\":\"Hall A\",\"capacity\":100,\"availableSeats\":100,\"availabilityStatus\":\"AVAILABLE\"}"))
           .andExpect(status().isOk());
    }

    @Test void getEnumValues() throws Exception {
        when(service.getTypes()).thenReturn(List.of(EventType.TECH, EventType.CULTURAL, EventType.SPORTS));
        mvc.perform(get("/events/types"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0]").value("TECH"));
    }

    @Test void getAllSecondaryRecords() throws Exception {
        Registration l = new Registration(); l.setRegistrationId("R1"); l.setParticipantName("Asha"); l.setParticipantEmail("asha@example.com"); l.setEventType(EventType.TECH); l.setSeatsRequested(1); l.setRegistrationDate("2026-11-01");
        when(service.getRegistrations()).thenReturn(List.of(l));
        mvc.perform(get("/registrations"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].registrationId").value("R1"));
    }

    @Test void addSecondaryRecord() throws Exception {
        Registration l = new Registration(); l.setRegistrationId("R1"); l.setParticipantName("Asha"); l.setParticipantEmail("asha@example.com"); l.setEventType(EventType.TECH); l.setSeatsRequested(1); l.setRegistrationDate("2026-11-01");
        given(service.addRegistration(any(Registration.class))).willReturn(l);
        mvc.perform(post("/registrations").contentType(MediaType.APPLICATION_JSON).content("{\"registrationId\":\"R1\",\"participantName\":\"Asha\",\"participantEmail\":\"asha@example.com\",\"eventType\":\"TECH\",\"seatsRequested\":1,\"registrationDate\":\"2026-11-01\"}"))
           .andExpect(status().isOk());
    }

    @Test void getAggregationCounts() throws Exception {
        mvc.perform(get("/registrations/count-by-type"))
           .andExpect(status().isOk());
    }
}
