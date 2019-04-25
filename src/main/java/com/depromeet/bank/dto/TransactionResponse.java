package com.depromeet.bank.dto;

import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.vo.TransactionValue;
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

    public static TransactionResponse from(TransactionValue transactionValue) {
        return new TransactionResponse(
                transactionValue.getAmount(),
                transactionValue.getDateTime(),
                transactionValue.getTransactionClassify(),
                transactionValue.getGuid(),
                transactionValue.getBalance()
        );
    }

}
