package com.example.bank.services;

import com.example.bank.models.Accounts;
import com.example.bank.models.TransactionDTO;
import com.example.bank.models.Transactions;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.schemas.TransactionSchema;
import com.example.bank.util.AmountParams;
import com.example.bank.util.CurrencyManager;
import com.example.bank.util.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Helpers helpers;

    @Transactional
    public List<TransactionDTO> getTransactionsByPage(String accountId,
                                                    Integer page, Integer size) throws Exception {
        Optional<Accounts> exists = accountRepository.findById(accountId);

        if (exists.isEmpty()) throw new Exception("Account not found!");

        Accounts account = exists.get();

        List<Transactions> transactions = new ArrayList<>();

        transactions.addAll(account.getSentTransactions());
        transactions.addAll(account.getReceivedTransactions());

        transactions.sort(Comparator.comparing(Transactions::getCreatedAt));

        // Add pagination logic if needed (page, size)

        List<TransactionDTO> finalTxns = new ArrayList<>();

        transactions.forEach(txn -> finalTxns.add(helpers.toDTO(txn)));

        return finalTxns;
    }

    public TransactionDTO getTransaction(String txnId) throws Exception {
        Optional<Transactions> transaction = repository.findById(txnId);

        if (transaction.isEmpty()) throw new Exception("Transaction not found!");

        return helpers.toDTO(transaction.get());
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionSchema schema) {
        Transactions txn = new Transactions();
        txn.setCreatedAt(new Date());

        try {
            Optional<Accounts> fromOpt = accountRepository.findByAccountNumber(schema.getFrom());
            Optional<Accounts> toOpt = accountRepository.findByAccountNumber(schema.getTo());

            if (fromOpt.isEmpty() || toOpt.isEmpty()) {
                txn.setStatus("FAILED");
                txn.setDescription("Account Not Found");
                return helpers.toDTO(repository.save(txn));
            }

            Accounts fromAccount = fromOpt.get();
            Accounts toAccount = toOpt.get();

            txn.setFromAccount(fromAccount);
            txn.setToAccount(toAccount);
            txn.setCurrency(fromAccount.getCurrencyCode());
            txn.setAmount(schema.getAmount());

            if (!fromAccount.getCurrencyCode().equals(toAccount.getCurrencyCode())) {
                txn.setStatus("FAILED");
                txn.setDescription("Currency mismatch between accounts");
                return helpers.toDTO(repository.save(txn));
            }

            long fromBalanceRaw = fromAccount.getBalanceRaw();
            long transferAmountRaw = CurrencyManager.toRawAmount(BigDecimal.valueOf(schema.getAmount()), fromAccount.getCurrencyCode());

            if (fromBalanceRaw < transferAmountRaw) {
                txn.setStatus("FAILED");
                txn.setDescription("Insufficient balance");
                return helpers.toDTO(repository.save(txn));
            }

            fromAccount.setBalanceRaw(fromBalanceRaw - transferAmountRaw);
            toAccount.setBalanceRaw(toAccount.getBalanceRaw() + transferAmountRaw);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            txn.setStatus("SUCCESS");
            txn.setDescription("Transfer completed");

        } catch (Exception e) {
            txn.setStatus("FAILED");
            txn.setDescription("Internal error: " + e.getMessage());
        }

        return helpers.toDTO(repository.save(txn));
    }
}
