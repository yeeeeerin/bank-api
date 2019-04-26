package com.depromeet.bank.service;

import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.dto.TransactionRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void createTransaction(Long memberId, TransactionRequest transactionRequest);

    void createTransaction(Long memberId, TransactionRequest transactionRequest, String name);

    List<Transaction> getTransaction(Long memberId, Long accountId, Pageable pageable);

    void deleteTransaction(Long memberId, UUID guid);
}
