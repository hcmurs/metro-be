package com.example.cronjob.Repository;

import com.example.cronjob.Pojos.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
    Transactions findByTransactionId(Long transactionId);
    // Custom query methods can be defined here if needed
    // For example, to find transactions by status or date range
}
