package com.example.library.service;
import com.example.library.model.*;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public Book addBook(Book book) {
        if (book.getTotalCopies() < 0 || book.getAvailableCopies() < 0 || book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Copy counts must be non-negative and available copies cannot exceed total copies");
        }
        if (book.getAvailabilityStatus() == null || book.getAvailabilityStatus().isBlank()) {
            book.setAvailabilityStatus(book.getAvailableCopies() > 0 ? "AVAILABLE" : "NOT_AVAILABLE");
        }
        return bookRepository.save(book);
    }

    public List<Book> getBooks() { return bookRepository.findAll(); }

    public List<BookCategory> getCategories() { return Arrays.asList(BookCategory.values()); }

    public Loan addLoan(Loan loan) {
        if (loan.getCopiesBorrowed() <= 0) throw new IllegalArgumentException("copiesBorrowed must be greater than zero");
        Book book = bookRepository.findFirstByCategory(loan.getCategory()).orElseThrow(() -> new IllegalArgumentException("Book category not found"));
        if (book.getAvailableCopies() < loan.getCopiesBorrowed()) throw new IllegalArgumentException("Not enough copies available");
        book.setAvailableCopies(book.getAvailableCopies() - loan.getCopiesBorrowed());
        book.setAvailabilityStatus(book.getAvailableCopies() == 0 ? "NOT_AVAILABLE" : "AVAILABLE");
        bookRepository.save(book);
        try { return loanRepository.save(loan); } catch (RuntimeException ex) {
            book.setAvailableCopies(book.getAvailableCopies() + loan.getCopiesBorrowed());
            book.setAvailabilityStatus("AVAILABLE"); bookRepository.save(book); throw ex;
        }
    }

    public List<Loan> getLoans() { return loanRepository.findAll(); }

    public List<LoanCount> getCounts() { return loanRepository.countLoansByCategory(); }
}
