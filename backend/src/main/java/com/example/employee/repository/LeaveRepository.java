package com.example.employee.repository;

import com.example.employee.model.LeaveRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;
import java.util.Map;

public interface LeaveRepository
        extends MongoRepository<LeaveRequest, String> {

    // Find leave requests by employee ID
    List<LeaveRequest> findByEmployeeId(String employeeId);

    // Find leave requests by type
    List<LeaveRequest> findByLeaveTypeIgnoreCase(String leaveType);

    // Find leave requests by status
    List<LeaveRequest> findByStatusIgnoreCase(String status);

    // Find leave requests by both type and status
    List<LeaveRequest> findByLeaveTypeIgnoreCaseAndStatusIgnoreCase(
            String leaveType, String status);

    // Custom MongoDB query
    @Query("{ 'leaveType': ?0, 'status': ?1 }")
    List<LeaveRequest> findUsingQuery(
            String leaveType, String status);

    // 1. Count requests by leave type
    @Aggregation(pipeline = {
            "{ '$match': { 'leaveType': { '$in': ['Sick', 'Regular', 'Casual'] } } }",
            "{ '$group': { '_id': '$leaveType', 'totalRequests': { '$sum': 1 } } }",
            "{ '$project': { '_id': 0, 'leaveType': '$_id', 'totalRequests': 1 } }",
            "{ '$sort': { 'leaveType': 1 } }"
    })
    List<Map<String, Object>> countByLeaveType();

    // 2. Count requests by leave type and status
    @Aggregation(pipeline = {
            "{ '$match': { 'leaveType': { '$in': ['Sick', 'Regular', 'Casual'] }, 'status': { '$in': ['Accepted', 'Rejected', 'Cancelled'] } } }",
            "{ '$group': { '_id': { 'leaveType': '$leaveType', 'status': '$status' }, 'totalRequests': { '$sum': 1 } } }",
            "{ '$project': { '_id': 0, 'leaveType': '$_id.leaveType', 'status': '$_id.status', 'totalRequests': 1 } }",
            "{ '$sort': { 'leaveType': 1, 'status': 1 } }"
    })
    List<Map<String, Object>> countByTypeAndStatus();

    // 3. Total accepted leave days for each employee
    @Aggregation(pipeline = {
            "{ '$match': { 'status': 'Accepted' } }",
            "{ '$group': { '_id': '$employeeId', 'totalAcceptedLeaveDays': { '$sum': '$numberOfDays' } } }",
            "{ '$lookup': { 'from': 'employees', 'localField': '_id', 'foreignField': '_id', 'as': 'employee' } }",
            "{ '$unwind': { 'path': '$employee', 'preserveNullAndEmptyArrays': true } }",
            "{ '$project': { '_id': 0, 'employeeId': '$_id', 'employeeName': '$employee.employeeName', 'totalAcceptedLeaveDays': 1 } }",
            "{ '$sort': { 'employeeName': 1 } }"
    })
    List<Map<String, Object>> totalAcceptedLeaveDays();
}
