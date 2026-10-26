package com.example.course.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public class Course {
    @Id
    private String courseId;
    private String courseId;
    private String courseName;
    private String instructorName;
    private CourseLevel courseLevel;
    private int capacity;
    private int availableSeats;
    private String availabilityStatus;

    public Course() {}
    public String getCourseId(){return courseId;} public void setCourseId(String v){courseId=v;}
public String getCourseName(){return courseName;} public void setCourseName(String v){courseName=v;}
public String getInstructorName(){return instructorName;} public void setInstructorName(String v){instructorName=v;}
public CourseLevel getCourseLevel(){return courseLevel;} public void setCourseLevel(CourseLevel v){courseLevel=v;}
public int getCapacity(){return capacity;} public void setCapacity(int v){capacity=v;}
public int getAvailableSeats(){return availableSeats;} public void setAvailableSeats(int v){availableSeats=v;}
public String getAvailabilityStatus(){return availabilityStatus;} public void setAvailabilityStatus(String v){availabilityStatus=v;}
}
