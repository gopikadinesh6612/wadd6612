package com.example.event.controller;
import com.example.event.model.Event;
import com.example.event.model.EventType;
import com.example.event.service.EventService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;
    public EventController(EventService service) { this.service = service; }

    @PostMapping
    public Event add(@RequestBody Event event) { return service.addEvent(event); }

    @GetMapping
    public List<Event> getAll() { return service.getEvents(); }

    @GetMapping("/types")
    public List<EventType> getTypes() { return service.getTypes(); }
}
