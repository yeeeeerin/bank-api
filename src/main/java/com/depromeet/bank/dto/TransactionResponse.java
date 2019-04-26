package com.depromeet.bank.dto;

import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class TransactionResponse {

    private Long amount;

    private LocalDateTime dateTime;

    private TransactionClassify transactionClassify;

    private String guid;

    private Long balance;

    private TransactionResponse(Long amount,
                                LocalDateTime dateTime,
                                TransactionClassify transactionClassify,
                                String guid,
                                Long balance) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.transactionClassify = transactionClassify;
        this.guid = guid;
        this.balance = balance;
    }

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getAmount(),
                transaction.getDateTime(),
                transaction.getTransactionClassify(),
                transaction.getGuid(),
                transaction.getBalance()
        );
    }

}
