package com.example.course.service;
import com.example.course.model.*;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    public CourseService(CourseRepository courseRepository, RegistrationRepository registrationRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    public Course addCourse(Course course) {
        if (course.getCapacity() <= 0 || course.getAvailableSeats() < 0 || course.getAvailableSeats() > course.getCapacity()) {
            throw new IllegalArgumentException("Capacity must be positive and available seats must be between zero and capacity");
        }
        if (course.getAvailabilityStatus() == null || course.getAvailabilityStatus().isBlank()) {
            course.setAvailabilityStatus(course.getAvailableSeats() > 0 ? "AVAILABLE" : "FULL");
        }
        return courseRepository.save(course);
    }

    public List<Course> getCourses() { return courseRepository.findAll(); }

    public List<CourseLevel> getLevels() { return Arrays.asList(CourseLevel.values()); }

    public Registration addRegistration(Registration registration) {
        if (registration.getSeatsRequested() <= 0) throw new IllegalArgumentException("seatsRequested must be greater than zero");
        Course course = courseRepository.findFirstByCourseLevel(registration.getCourseLevel()).orElseThrow(() -> new IllegalArgumentException("Course level not found"));
        if (course.getAvailableSeats() < registration.getSeatsRequested()) throw new IllegalArgumentException("Not enough seats available");
        course.setAvailableSeats(course.getAvailableSeats() - registration.getSeatsRequested());
        course.setAvailabilityStatus(course.getAvailableSeats() == 0 ? "FULL" : "AVAILABLE");
        courseRepository.save(course);
        try { return registrationRepository.save(registration); } catch (RuntimeException ex) {
            course.setAvailableSeats(course.getAvailableSeats() + registration.getSeatsRequested());
            course.setAvailabilityStatus("AVAILABLE"); courseRepository.save(course); throw ex;
        }
    }

    public List<Registration> getRegistrations() { return registrationRepository.findAll(); }

    public List<RegistrationCount> getCounts() { return registrationRepository.countRegistrationsByCourseLevel(); }
}
