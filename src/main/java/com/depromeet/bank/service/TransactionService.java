package com.depromeet.bank.service;

import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void createTransaction(Long memberId, TransactionRequest transactionRequest);

    List<TransactionResponse> getTransaction(Long memberId, Long accountId, int page);

    void deleteTransaction(Long memberId, UUID guid);
}
