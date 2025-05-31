package com.example.bank.services;

import com.example.bank.models.*;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;
import com.example.bank.util.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Helpers helper;

    public List<AccountModel> fetchAccounts() {
        List<Accounts> accounts = repository.findByUser_Username("");

        List<AccountModel> finalAccounts = new ArrayList<>();

        accounts.stream().forEach(account -> {
            Balances balance = account.getBalance();
            Double amount = balance.getRaw() / Math.pow(10,balance.getDecimals());
            finalAccounts.add(AccountModel
                    .builder()
                    .id(account.getId())
                    .accountNumber(account.getAccountNumber())
                    .status(account.getStatus())
                    .balance(amount)
                    .accountType(account.getAccountType())
                    .createdAt(account.getCreatedAt())
                    .build()
            );
        });

        return finalAccounts;
    }

    public AccountModel fetchAccount(String id) throws Exception {
        Optional<Accounts> exists =  repository.findById(id);

        if(exists.isEmpty()) throw new Exception("Account Not found!");

        Accounts account = exists.get();
        Balances balance = account.getBalance();

        Double amount = balance.getRaw() / Math.pow(10,balance.getDecimals());

        return AccountModel
                .builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .status(account.getStatus())
                .balance(amount)
                .createdAt(account.getCreatedAt())
                .id(account.getId())
                .build();
    }

    public Double fetchAccountBalance(String id) throws Exception {
        Optional<Accounts> exists =  repository.findById(id);

        if(exists.isEmpty()) throw new Exception("Account Not found!");

        Accounts account = exists.get();
        Balances balance = account.getBalance();

        return balance.getRaw() / Math.pow(10,balance.getDecimals());
    }

    public AccountModel saveAccount(AccountRequest accountBody) throws Exception {
        Optional<Users> exists = userRepository.findByUsername(accountBody.getUsername());

        if(exists.isEmpty()) throw new Exception("User Not found!");

        Users user = exists.get();

        Accounts accounts = Accounts
                .builder()
                .user(user)
                .accountType(accountBody.getAccountType())
                .accountNumber(helper.generateUniqueAccountNumber())
                .status("OPERATIONAL")
                .createdAt(new Date())
                .balance(Balances.builder()
                        .raw(accountBody.getDeposit().longValue())
                        .decimals(new BigDecimal(accountBody.getDeposit()).stripTrailingZeros().scale())
                        .build()
                )
                .sentTransactions(List.of())
                .receivedTransactions(List.of())
                .build();

        return AccountModel.builder()
                .accountType(accounts.getAccountType())
                .accountNumber(accounts.getAccountNumber())
                .status(accounts.getStatus())
                .balance(accountBody.getDeposit())
                .createdAt(accounts.getCreatedAt())
                .build();
    }
}
