package com.example.course.controller;
import com.example.course.model.Course;
import com.example.course.model.CourseLevel;
import com.example.course.service.CourseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService service;
    public CourseController(CourseService service) { this.service = service; }

    @PostMapping
    public Course add(@RequestBody Course course) { return service.addCourse(course); }

    @GetMapping
    public List<Course> getAll() { return service.getCourses(); }

    @GetMapping("/levels")
    public List<CourseLevel> getTypes() { return service.getLevels(); }
}
