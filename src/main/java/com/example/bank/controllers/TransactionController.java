package com.example.bank.controllers;

import com.example.bank.models.Transactions;
import com.example.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/transactions")
    public List<Transactions> getTransactions(@RequestParam(name = "id") String accountId,
              @RequestParam Integer page, @RequestParam Integer size)  {

        try {
            return service.getTransactionsByPage(accountId,page,size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/transactions/{id}")
    public Transactions getTransactions(@RequestParam String accountId)  {

        try {
            return service.getTransaction(accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
