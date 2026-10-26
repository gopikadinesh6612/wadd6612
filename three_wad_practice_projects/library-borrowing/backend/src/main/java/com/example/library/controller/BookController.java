package com.example.library.controller;
import com.example.library.model.Book;
import com.example.library.model.BookCategory;
import com.example.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookController {
    private final LibraryService service;
    public BookController(LibraryService service) { this.service = service; }

    @PostMapping
    public Book add(@RequestBody Book book) { return service.addBook(book); }

    @GetMapping
    public List<Book> getAll() { return service.getBooks(); }

    @GetMapping("/categories")
    public List<BookCategory> getTypes() { return service.getCategories(); }
}
