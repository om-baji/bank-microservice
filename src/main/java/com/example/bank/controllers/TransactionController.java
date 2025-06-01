package com.example.bank.controllers;

import com.example.bank.models.TransactionDTO;
import com.example.bank.models.Transactions;
import com.example.bank.schemas.TransactionSchema;
import com.example.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(@RequestParam(name = "id") String accountId,
              @RequestParam Integer page, @RequestParam Integer size)  {

        try {
            return service.getTransactionsByPage(accountId,page,size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransactions(@RequestParam String txnId) {
        try {
            return service.getTransaction(txnId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/execute")
    public TransactionDTO postTransaction(@RequestBody TransactionSchema schema) {
        try {
            return service.createTransaction(schema);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}