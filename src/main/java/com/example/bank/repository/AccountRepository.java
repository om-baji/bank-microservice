package com.example.bank.repository;

import com.example.bank.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,String> {

    List<Accounts> findByUser_Username(String username);

    boolean existsByAccountNumber(String accountNumber);
}
