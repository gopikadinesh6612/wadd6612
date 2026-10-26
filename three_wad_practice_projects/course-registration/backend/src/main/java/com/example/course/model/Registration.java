package com.example.course.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registrations")
public class Registration {
    @Id
    private String registrationId;
    private String registrationId;
    private String studentName;
    private String studentEmail;
    private CourseLevel courseLevel;
    private int seatsRequested;
    private String registrationDate;

    public Registration() {}
    public String getRegistrationId(){return registrationId;} public void setRegistrationId(String v){registrationId=v;}
public String getStudentName(){return studentName;} public void setStudentName(String v){studentName=v;}
public String getStudentEmail(){return studentEmail;} public void setStudentEmail(String v){studentEmail=v;}
public CourseLevel getCourseLevel(){return courseLevel;} public void setCourseLevel(CourseLevel v){courseLevel=v;}
public int getSeatsRequested(){return seatsRequested;} public void setSeatsRequested(int v){seatsRequested=v;}
public String getRegistrationDate(){return registrationDate;} public void setRegistrationDate(String v){registrationDate=v;}
}
