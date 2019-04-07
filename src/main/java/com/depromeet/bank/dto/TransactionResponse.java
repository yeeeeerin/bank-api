package com.depromeet.bank.dto;

import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;

import java.time.LocalDateTime;
import java.util.UUID;


public class TransactionResponse {

    private Long amount;

    private LocalDateTime dateTime;

    private TransactionClassify transactionClassify;

    private UUID guid;

    private TransactionResponse(Long amount, LocalDateTime dateTime, TransactionClassify transactionClassify, UUID guid) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.transactionClassify = transactionClassify;
    }

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(transaction.getAmount(), transaction.getDateTime(), transaction.getTransactionClassify(), transaction.getGuid());
    }

}
