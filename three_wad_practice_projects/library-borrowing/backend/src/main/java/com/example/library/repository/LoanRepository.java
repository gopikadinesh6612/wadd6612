package com.example.library.repository;
import com.example.library.model.Loan;
import com.example.library.model.LoanCount;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String> {
    @Aggregation(pipeline = {
        "{ '$group': { '_id': '$category', 'count': { '$sum': 1 } } }",
        "{ '$project': { '_id': 0, 'category': '$_id', 'count': 1 } }",
        "{ '$sort': { 'category': 1 } }"
    })
    List<LoanCount> countLoansByCategory();
}
