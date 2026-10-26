package com.example.employee.controller;

import com.example.employee.model.LeaveRequest;
import com.example.employee.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaves")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    @PostMapping
    public LeaveRequest applyLeave(@RequestBody LeaveRequest leave) {
        return service.applyLeave(leave);
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaves(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String employeeId) {

        if (type != null && status != null) {
            return service.getByTypeAndStatus(type, status);
        }
        if (type != null) {
            return service.getByType(type);
        }
        if (status != null) {
            return service.getByStatus(status);
        }
        if (employeeId != null) {
            return service.getByEmployee(employeeId);
        }
        return service.getAllLeaves();
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getByEmployee(
            @PathVariable String employeeId) {
        return service.getByEmployee(employeeId);
    }

    @GetMapping("/analytics/employees-by-type")
    public List<LeaveRequest> employeesByType(
            @RequestParam String type) {
        return service.getEmployeesByType(type);
    }

    @GetMapping("/analytics/employees-by-status")
    public List<LeaveRequest> employeesByStatus(
            @RequestParam String status) {
        return service.getEmployeesByStatus(status);
    }

    @GetMapping("/analytics/count-by-type")
    public List<Map<String, Object>> countByType() {
        return service.countByType();
    }

    @GetMapping("/analytics/type-status")
    public List<Map<String, Object>> countByTypeAndStatus() {
        return service.countByTypeAndStatus();
    }

    @GetMapping("/analytics/employee-leave-days")
    public List<Map<String, Object>> totalAcceptedLeaveDays() {
        return service.totalAcceptedLeaveDays();
    }

    @PatchMapping("/{leaveId}/status")
    public LeaveRequest updateStatus(
            @PathVariable String leaveId,
            @RequestParam String status) {
        return service.updateStatus(leaveId, status);
    }
}