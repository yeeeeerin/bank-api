package com.depromeet.bank.service;

import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.vo.TransactionValue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void createTransaction(Long memberId, TransactionRequest transactionRequest);

    List<TransactionValue> getTransaction(Long memberId, Long accountId, Pageable pageable);

    void deleteTransaction(Long memberId, UUID guid);
}
