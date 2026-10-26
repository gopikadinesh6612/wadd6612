package com.example.event.repository;
import com.example.event.model.Registration;
import com.example.event.model.RegistrationCount;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    @Aggregation(pipeline = {
        "{ '$group': { '_id': '$eventType', 'count': { '$sum': 1 } } }",
        "{ '$project': { '_id': 0, 'eventType': '$_id', 'count': 1 } }",
        "{ '$sort': { 'eventType': 1 } }"
    })
    List<RegistrationCount> countRegistrationsByEventType();
}
