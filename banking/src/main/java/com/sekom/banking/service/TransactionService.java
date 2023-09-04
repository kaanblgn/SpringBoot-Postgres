package com.sekom.banking.service;

import com.sekom.banking.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    Optional<Transaction> getTransactionById(UUID transactionId);
    List<Transaction> getAllTransactions();
    Transaction createTransaction(Transaction transaction);
    Transaction updateTransaction(Transaction transaction);
    void deleteTransaction(Transaction transaction);
    List<Transaction> getAccountTransactions(UUID accountId);

}

