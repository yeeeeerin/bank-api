package com.depromeet.bank.service;

import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    UUID createTransaction(TransactionRequest transactionRequest);

    List<TransactionResponse> getTransaction(Long accountId, int page);

    void deleteTransaction(UUID guid);
}
