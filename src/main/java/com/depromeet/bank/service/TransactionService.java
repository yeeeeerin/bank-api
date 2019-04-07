package com.depromeet.bank.service;

import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.dto.TransactionRequest;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void createTransaction(TransactionRequest transactionRequest);

    List<Transaction> getTransaction(Long accountId, int page);

    void deleteTransaction(UUID guid);
}
