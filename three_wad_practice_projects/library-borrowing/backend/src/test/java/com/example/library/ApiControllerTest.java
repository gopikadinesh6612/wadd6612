package com.example.library;
import com.example.library.controller.*;
import com.example.library.model.*;
import com.example.library.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BookController.class, LoanController.class})
class ApiControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean LibraryService service;

    @Test void getAllPrimaryRecords() throws Exception {
        Book b = new Book(); b.setBookId("B1"); b.setTitle("Test Book"); b.setAuthor("Author"); b.setCategory(BookCategory.FICTION); b.setTotalCopies(4); b.setAvailableCopies(4); b.setAvailabilityStatus("AVAILABLE");
        when(service.getBooks()).thenReturn(List.of(b));
        mvc.perform(get("/books"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].bookId").value("B1"));
    }

    @Test void addPrimaryRecord() throws Exception {
        Book b = new Book(); b.setBookId("B1"); b.setTitle("Test Book"); b.setAuthor("Author"); b.setCategory(BookCategory.FICTION); b.setTotalCopies(4); b.setAvailableCopies(4); b.setAvailabilityStatus("AVAILABLE");
        given(service.addBook(any(Book.class))).willReturn(b);
        mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content("{\"bookId\":\"B1\",\"title\":\"Test Book\",\"author\":\"Author\",\"category\":\"FICTION\",\"totalCopies\":4,\"availableCopies\":4,\"availabilityStatus\":\"AVAILABLE\"}"))
           .andExpect(status().isOk());
    }

    @Test void getEnumValues() throws Exception {
        when(service.getCategories()).thenReturn(List.of(BookCategory.FICTION, BookCategory.SCIENCE, BookCategory.HISTORY));
        mvc.perform(get("/books/categories"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0]").value("FICTION"));
    }

    @Test void getAllSecondaryRecords() throws Exception {
        Loan l = new Loan(); l.setLoanId("L1"); l.setStudentName("Asha"); l.setStudentEmail("asha@example.com"); l.setCategory(BookCategory.FICTION); l.setCopiesBorrowed(1); l.setBorrowDate("2026-11-01");
        when(service.getLoans()).thenReturn(List.of(l));
        mvc.perform(get("/loans"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].loanId").value("L1"));
    }

    @Test void addSecondaryRecord() throws Exception {
        Loan l = new Loan(); l.setLoanId("L1"); l.setStudentName("Asha"); l.setStudentEmail("asha@example.com"); l.setCategory(BookCategory.FICTION); l.setCopiesBorrowed(1); l.setBorrowDate("2026-11-01");
        given(service.addLoan(any(Loan.class))).willReturn(l);
        mvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content("{\"loanId\":\"L1\",\"studentName\":\"Asha\",\"studentEmail\":\"asha@example.com\",\"category\":\"FICTION\",\"copiesBorrowed\":1,\"borrowDate\":\"2026-11-01\"}"))
           .andExpect(status().isOk());
    }

    @Test void getAggregationCounts() throws Exception {
        mvc.perform(get("/loans/count-by-category"))
           .andExpect(status().isOk());
    }
}
