package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Test
    void testGetAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("E101");
        employee.setEmployeeName("Anusha");

        when(service.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value("E101"))
                .andExpect(jsonPath("$[0].employeeName").value("Anusha"));
    }

    @Test
    void testAddEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("E101");
        employee.setEmployeeName("Anusha");

        when(service.addEmployee(any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "employeeId":"E101",
                      "employeeName":"Anusha",
                      "sickLeaveBalance":10,
                      "regularLeaveBalance":12,
                      "casualLeaveBalance":8
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("E101"))
                .andExpect(jsonPath("$.employeeName").value("Anusha"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("E101");
        employee.setEmployeeName("Anusha");

        when(service.getEmployeeById("E101")).thenReturn(employee);

        mockMvc.perform(get("/employees/E101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value("E101"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId("E101");
        employee.setEmployeeName("Anusha Updated");

        when(service.updateEmployee(eq("E101"), any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(put("/employees/E101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "employeeName":"Anusha Updated",
                      "sickLeaveBalance":10,
                      "regularLeaveBalance":12,
                      "casualLeaveBalance":8
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("Anusha Updated"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(service).deleteEmployee("E101");

        mockMvc.perform(delete("/employees/E101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }
}