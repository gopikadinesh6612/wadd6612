package com.example.event.repository;
import com.example.event.model.Event;
import com.example.event.model.EventType;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findFirstByEventType(EventType eventType);
}
