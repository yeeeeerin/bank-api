package com.depromeet.bank.service;

import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void createTransaction(TransactionRequest transactionRequest, Long memberId);

    List<TransactionResponse> getTransaction(Long accountId, int page);

    void deleteTransaction(UUID guid);
}
