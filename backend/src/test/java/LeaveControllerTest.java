package com.example.employee.controller;

import com.example.employee.model.LeaveRequest;
import com.example.employee.service.LeaveService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LeaveController.class)
class LeaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaveService service;

    @Test
    void testGetAllLeaves() throws Exception {
        LeaveRequest leave = new LeaveRequest();
        leave.setLeaveId("L101");
        leave.setEmployeeId("E101");
        leave.setLeaveType("Sick");
        leave.setStatus("Accepted");

        when(service.getAllLeaves()).thenReturn(List.of(leave));

        mockMvc.perform(get("/leaves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].leaveId").value("L101"))
                .andExpect(jsonPath("$[0].status").value("Accepted"));
    }

    @Test
    void testApplyLeave() throws Exception {
        LeaveRequest leave = new LeaveRequest();
        leave.setLeaveId("L101");
        leave.setEmployeeId("E101");
        leave.setLeaveType("Sick");
        leave.setStatus("Accepted");
        leave.setNumberOfDays(2);

        when(service.applyLeave(any(LeaveRequest.class))).thenReturn(leave);

        mockMvc.perform(post("/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "employeeId":"E101",
                      "leaveType":"Sick",
                      "startDate":"2026-11-02",
                      "endDate":"2026-11-03"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leaveId").value("L101"))
                .andExpect(jsonPath("$.status").value("Accepted"))
                .andExpect(jsonPath("$.numberOfDays").value(2));
    }

    @Test
    void testFilterByTypeAndStatus() throws Exception {
        LeaveRequest leave = new LeaveRequest();
        leave.setLeaveType("Sick");
        leave.setStatus("Accepted");

        when(service.getByTypeAndStatus("Sick", "Accepted"))
                .thenReturn(List.of(leave));

        mockMvc.perform(get("/leaves")
                        .param("type", "Sick")
                        .param("status", "Accepted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].leaveType").value("Sick"))
                .andExpect(jsonPath("$[0].status").value("Accepted"));
    }

    @Test
    void testGetLeavesByEmployee() throws Exception {
        LeaveRequest leave = new LeaveRequest();
        leave.setEmployeeId("E101");

        when(service.getByEmployee("E101")).thenReturn(List.of(leave));

        mockMvc.perform(get("/leaves/employee/E101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value("E101"));
    }

    @Test
    void testCountByType() throws Exception {
        when(service.countByType()).thenReturn(List.of(
                Map.of("leaveType", "Sick", "totalRequests", 5L),
                Map.of("leaveType", "Regular", "totalRequests", 3L),
                Map.of("leaveType", "Casual", "totalRequests", 4L)
        ));

        mockMvc.perform(get("/leaves/analytics/count-by-type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].leaveType").value("Sick"))
                .andExpect(jsonPath("$[0].totalRequests").value(5));
    }

    @Test
    void testCountByTypeAndStatus() throws Exception {
        when(service.countByTypeAndStatus()).thenReturn(List.of(
                Map.of("leaveType", "Sick",
                        "status", "Accepted",
                        "totalRequests", 2L)
        ));

        mockMvc.perform(get("/leaves/analytics/type-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Accepted"));
    }

    @Test
    void testUpdateLeaveStatus() throws Exception {
        LeaveRequest leave = new LeaveRequest();
        leave.setLeaveId("L101");
        leave.setStatus("Rejected");

        when(service.updateStatus("L101", "Rejected")).thenReturn(leave);

        mockMvc.perform(patch("/leaves/L101/status")
                        .param("status", "Rejected"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Rejected"));
    }
}