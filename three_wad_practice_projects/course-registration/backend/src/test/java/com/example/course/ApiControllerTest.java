package com.example.course;
import com.example.course.controller.*;
import com.example.course.model.*;
import com.example.course.service.CourseService;
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

@WebMvcTest(controllers = {CourseController.class, RegistrationController.class})
class ApiControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean CourseService service;

    @Test void getAllPrimaryRecords() throws Exception {
        Course b = new Course(); b.setCourseId("C1"); b.setCourseName("Java Basics"); b.setInstructorName("Ravi"); b.setCourseLevel(CourseLevel.BEGINNER); b.setCapacity(30); b.setAvailableSeats(30); b.setAvailabilityStatus("AVAILABLE");
        when(service.getCourses()).thenReturn(List.of(b));
        mvc.perform(get("/courses"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].courseId").value("C1"));
    }

    @Test void addPrimaryRecord() throws Exception {
        Course b = new Course(); b.setCourseId("C1"); b.setCourseName("Java Basics"); b.setInstructorName("Ravi"); b.setCourseLevel(CourseLevel.BEGINNER); b.setCapacity(30); b.setAvailableSeats(30); b.setAvailabilityStatus("AVAILABLE");
        given(service.addCourse(any(Course.class))).willReturn(b);
        mvc.perform(post("/courses").contentType(MediaType.APPLICATION_JSON).content("{\"courseId\":\"C1\",\"courseName\":\"Java Basics\",\"instructorName\":\"Ravi\",\"courseLevel\":\"BEGINNER\",\"capacity\":30,\"availableSeats\":30,\"availabilityStatus\":\"AVAILABLE\"}"))
           .andExpect(status().isOk());
    }

    @Test void getEnumValues() throws Exception {
        when(service.getLevels()).thenReturn(List.of(CourseLevel.BEGINNER, CourseLevel.INTERMEDIATE, CourseLevel.ADVANCED));
        mvc.perform(get("/courses/levels"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0]").value("BEGINNER"));
    }

    @Test void getAllSecondaryRecords() throws Exception {
        Registration l = new Registration(); l.setRegistrationId("R1"); l.setStudentName("Asha"); l.setStudentEmail("asha@example.com"); l.setCourseLevel(CourseLevel.BEGINNER); l.setSeatsRequested(1); l.setRegistrationDate("2026-11-01");
        when(service.getRegistrations()).thenReturn(List.of(l));
        mvc.perform(get("/registrations"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].registrationId").value("R1"));
    }

    @Test void addSecondaryRecord() throws Exception {
        Registration l = new Registration(); l.setRegistrationId("R1"); l.setStudentName("Asha"); l.setStudentEmail("asha@example.com"); l.setCourseLevel(CourseLevel.BEGINNER); l.setSeatsRequested(1); l.setRegistrationDate("2026-11-01");
        given(service.addRegistration(any(Registration.class))).willReturn(l);
        mvc.perform(post("/registrations").contentType(MediaType.APPLICATION_JSON).content("{\"registrationId\":\"R1\",\"studentName\":\"Asha\",\"studentEmail\":\"asha@example.com\",\"courseLevel\":\"BEGINNER\",\"seatsRequested\":1,\"registrationDate\":\"2026-11-01\"}"))
           .andExpect(status().isOk());
    }

    @Test void getAggregationCounts() throws Exception {
        mvc.perform(get("/registrations/count-by-level"))
           .andExpect(status().isOk());
    }
}
