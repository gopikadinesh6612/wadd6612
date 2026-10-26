package com.example.course.repository;
import com.example.course.model.Registration;
import com.example.course.model.RegistrationCount;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    @Aggregation(pipeline = {
        "{ '$group': { '_id': '$courseLevel', 'count': { '$sum': 1 } } }",
        "{ '$project': { '_id': 0, 'courseLevel': '$_id', 'count': 1 } }",
        "{ '$sort': { 'courseLevel': 1 } }"
    })
    List<RegistrationCount> countRegistrationsByCourseLevel();
}
