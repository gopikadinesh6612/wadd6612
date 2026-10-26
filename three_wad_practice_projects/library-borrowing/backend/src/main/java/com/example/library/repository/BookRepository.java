package com.example.library.repository;
import com.example.library.model.Book;
import com.example.library.model.BookCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findFirstByCategory(BookCategory category);
}
