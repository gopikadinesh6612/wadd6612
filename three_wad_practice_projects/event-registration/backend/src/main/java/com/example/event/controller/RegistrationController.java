package com.example.event.controller;
import com.example.event.model.Registration;
import com.example.event.model.RegistrationCount;
import com.example.event.service.EventService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final EventService service;
    public RegistrationController(EventService service) { this.service = service; }

    @PostMapping
    public Registration add(@RequestBody Registration registration) { return service.addRegistration(registration); }

    @GetMapping
    public List<Registration> getAll() { return service.getRegistrations(); }

    @GetMapping("/count-by-type")
    public List<RegistrationCount> counts() { return service.getCounts(); }
}
