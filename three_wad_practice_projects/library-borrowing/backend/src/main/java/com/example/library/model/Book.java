package com.example.library.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
    @Id
    private String bookId;
    private String bookId;
    private String title;
    private String author;
    private BookCategory category;
    private int totalCopies;
    private int availableCopies;
    private String availabilityStatus;

    public Book() {}
    public String getBookId(){return bookId;} public void setBookId(String v){bookId=v;}
public String getTitle(){return title;} public void setTitle(String v){title=v;}
public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
public BookCategory getCategory(){return category;} public void setCategory(BookCategory v){category=v;}
public int getTotalCopies(){return totalCopies;} public void setTotalCopies(int v){totalCopies=v;}
public int getAvailableCopies(){return availableCopies;} public void setAvailableCopies(int v){availableCopies=v;}
public String getAvailabilityStatus(){return availabilityStatus;} public void setAvailabilityStatus(String v){availabilityStatus=v;}
}
