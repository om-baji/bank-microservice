package com.example.bank.services;

import com.example.bank.models.Accounts;
import com.example.bank.models.Transactions;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transactions> getTransactionsByPage(String accountId, Integer page,
                                                    Integer size) throws Exception {
      Optional<Accounts> exists = accountRepository.findById(accountId);

      if(exists.isEmpty()) throw new Exception("Account not found!");

      Accounts account = exists.get();

      List<Transactions> transactions = new ArrayList<>();

      transactions.addAll(account.getSentTransactions());
      transactions.addAll(account.getReceivedTransactions());

      transactions.sort(Comparator.comparing(Transactions::getCreatedAt));

      return transactions;
    }

    public Transactions getTransaction(String txnId) throws Exception {
        Optional<Transactions> transaction = repository.findById(txnId);

        if (transaction.isEmpty()) throw  new Exception("Transaction not found!");

        return transaction.get();
    }
}
