package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.LeaveRequest;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.LeaveRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveService(
            LeaveRepository leaveRepository,
            EmployeeRepository employeeRepository) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }

    public LeaveRequest applyLeave(LeaveRequest leave) {
        Employee employee = employeeRepository
                .findById(leave.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee not found"));

        String type = normalizeType(leave.getLeaveType());
        leave.setLeaveType(type);

        LocalDate start = LocalDate.parse(leave.getStartDate());
        LocalDate end = LocalDate.parse(leave.getEndDate());

        if (end.isBefore(start)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date");
        }

        // Count both the start date and the end date.
        int days = Math.toIntExact(
                ChronoUnit.DAYS.between(start, end) + 1);

        leave.setNumberOfDays(days);

        int balance = getBalance(employee, type);

        if (balance >= days) {
            setBalance(employee, type, balance - days);
            leave.setStatus("Accepted");
            employeeRepository.save(employee);
        } else {
            leave.setStatus("Cancelled");
        }

        // Save the request in either case.
        leave.setLeaveId(null);
        return leaveRepository.save(leave);
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public List<LeaveRequest> getByEmployee(String employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public List<LeaveRequest> getByType(String type) {
        return leaveRepository.findByLeaveTypeIgnoreCase(
                normalizeType(type));
    }

    public List<LeaveRequest> getByStatus(String status) {
        return leaveRepository.findByStatusIgnoreCase(
                normalizeStatus(status));
    }

    public List<LeaveRequest> getByTypeAndStatus(
            String type, String status) {
        return leaveRepository.findByLeaveTypeIgnoreCaseAndStatusIgnoreCase(
                normalizeType(type), normalizeStatus(status));
    }

    public List<LeaveRequest> getUsingQuery(
            String type, String status) {
        return leaveRepository.findUsingQuery(
                normalizeType(type), normalizeStatus(status));
    }

    public List<LeaveRequest> getEmployeesByType(String type) {
        return getByType(type);
    }

    public List<LeaveRequest> getEmployeesByStatus(String status) {
        return getByStatus(status);
    }

    public List<Map<String, Object>> countByType() {
        return leaveRepository.countByLeaveType();
    }

    public List<Map<String, Object>> countByTypeAndStatus() {
        return leaveRepository.countByTypeAndStatus();
    }

    public List<Map<String, Object>> totalAcceptedLeaveDays() {
        return leaveRepository.totalAcceptedLeaveDays();
    }

    public LeaveRequest updateStatus(String leaveId, String newStatus) {
        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Leave request not found"));

        String status = normalizeStatus(newStatus);

        if (!status.equals("Accepted") && !status.equals("Rejected")) {
            throw new IllegalArgumentException(
                    "Status must be Accepted or Rejected");
        }

        if (leave.getStatus().equals(status)) {
            return leave;
        }

        Employee employee = employeeRepository
                .findById(leave.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee not found"));

        if (status.equals("Rejected")
                && leave.getStatus().equals("Accepted")) {
            int balance = getBalance(employee, leave.getLeaveType());
            setBalance(employee, leave.getLeaveType(),
                    balance + leave.getNumberOfDays());
            employeeRepository.save(employee);
        }

        if (status.equals("Accepted")
                && !leave.getStatus().equals("Accepted")) {
            int balance = getBalance(employee, leave.getLeaveType());

            if (balance < leave.getNumberOfDays()) {
                throw new IllegalArgumentException(
                        "Insufficient leave balance");
            }

            setBalance(employee, leave.getLeaveType(),
                    balance - leave.getNumberOfDays());
            employeeRepository.save(employee);
        }

        leave.setStatus(status);
        return leaveRepository.save(leave);
    }

    private String normalizeType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Leave type is required");
        }

        return switch (type.trim().toLowerCase()) {
            case "sick", "sick leave" -> "Sick";
            case "regular", "regular leave" -> "Regular";
            case "casual", "casual leave" -> "Casual";
            default -> throw new IllegalArgumentException(
                    "Type must be Sick, Regular or Casual");
        };
    }

    private String normalizeStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }

        return switch (status.trim().toLowerCase()) {
            case "accepted" -> "Accepted";
            case "rejected" -> "Rejected";
            case "cancelled", "canceled" -> "Cancelled";
            default -> throw new IllegalArgumentException(
                    "Invalid leave status");
        };
    }

    private int getBalance(Employee employee, String type) {
        return switch (type) {
            case "Sick" -> employee.getSickLeaveBalance();
            case "Regular" -> employee.getRegularLeaveBalance();
            case "Casual" -> employee.getCasualLeaveBalance();
            default -> throw new IllegalArgumentException("Invalid leave type");
        };
    }

    private void setBalance(Employee employee, String type, int balance) {
        switch (type) {
            case "Sick" -> employee.setSickLeaveBalance(balance);
            case "Regular" -> employee.setRegularLeaveBalance(balance);
            case "Casual" -> employee.setCasualLeaveBalance(balance);
            default -> throw new IllegalArgumentException("Invalid leave type");
        }
    }
}