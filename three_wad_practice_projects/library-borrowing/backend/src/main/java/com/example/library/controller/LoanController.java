package com.example.library.controller;
import com.example.library.model.Loan;
import com.example.library.model.LoanCount;
import com.example.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LibraryService service;
    public LoanController(LibraryService service) { this.service = service; }

    @PostMapping
    public Loan add(@RequestBody Loan loan) { return service.addLoan(loan); }

    @GetMapping
    public List<Loan> getAll() { return service.getLoans(); }

    @GetMapping("/count-by-category")
    public List<LoanCount> counts() { return service.getCounts(); }
}
