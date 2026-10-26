package com.example.course.repository;
import com.example.course.model.Course;
import com.example.course.model.CourseLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findFirstByCourseLevel(CourseLevel courseLevel);
}
