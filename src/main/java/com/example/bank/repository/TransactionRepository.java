package com.example.bank.repository;

import com.example.bank.models.Accounts;
import com.example.bank.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,String> {

    Optional<Transactions> findByAccountId(String accountId);
}
