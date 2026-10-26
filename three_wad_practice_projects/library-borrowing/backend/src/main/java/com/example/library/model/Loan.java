package com.example.library.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "loans")
public class Loan {
    @Id
    private String loanId;
    private String loanId;
    private String studentName;
    private String studentEmail;
    private BookCategory category;
    private int copiesBorrowed;
    private String borrowDate;

    public Loan() {}
    public String getLoanId(){return loanId;} public void setLoanId(String v){loanId=v;}
public String getStudentName(){return studentName;} public void setStudentName(String v){studentName=v;}
public String getStudentEmail(){return studentEmail;} public void setStudentEmail(String v){studentEmail=v;}
public BookCategory getCategory(){return category;} public void setCategory(BookCategory v){category=v;}
public int getCopiesBorrowed(){return copiesBorrowed;} public void setCopiesBorrowed(int v){copiesBorrowed=v;}
public String getBorrowDate(){return borrowDate;} public void setBorrowDate(String v){borrowDate=v;}
}
