package com.example.bank.controllers;

import com.example.bank.models.AccountModel;
import com.example.bank.models.AccountRequest;
import com.example.bank.models.Accounts;
import com.example.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountModel> getAccounts() {
        return accountService.fetchAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountModel getAccount(@PathVariable String id) {
        try {
            return accountService.fetchAccount(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/accounts/{id}/balance")
    public Double getBalance(@PathVariable String id) {
        try {
            return accountService.fetchAccountBalance(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/account")
    public AccountModel postAccount(@RequestBody AccountRequest accountBody){
        try {
            return accountService.saveAccount(accountBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
