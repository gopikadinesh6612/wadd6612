package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee addEmployee(Employee employee) {
        if (repository.existsById(employee.getEmployeeId())) {
            throw new IllegalArgumentException("Employee ID already exists");
        }
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Employee not found"));
    }

    public Employee updateEmployee(String id, Employee updated) {
        Employee employee = getEmployeeById(id);
        employee.setEmployeeName(updated.getEmployeeName());
        employee.setSickLeaveBalance(updated.getSickLeaveBalance());
        employee.setRegularLeaveBalance(updated.getRegularLeaveBalance());
        employee.setCasualLeaveBalance(updated.getCasualLeaveBalance());
        return repository.save(employee);
    }

    public void deleteEmployee(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found");
        }
        repository.deleteById(id);
    }
}