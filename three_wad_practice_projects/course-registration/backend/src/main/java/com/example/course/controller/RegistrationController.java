package com.example.course.controller;
import com.example.course.model.Registration;
import com.example.course.model.RegistrationCount;
import com.example.course.service.CourseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final CourseService service;
    public RegistrationController(CourseService service) { this.service = service; }

    @PostMapping
    public Registration add(@RequestBody Registration registration) { return service.addRegistration(registration); }

    @GetMapping
    public List<Registration> getAll() { return service.getRegistrations(); }

    @GetMapping("/count-by-level")
    public List<RegistrationCount> counts() { return service.getCounts(); }
}
