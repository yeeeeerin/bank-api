package com.depromeet.bank.vo;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.TransactionClassify;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@ToString
@Value
public class TransactionValue {
    private Long amount;
    private TransactionClassify transactionClassify;
    private Account account;
    private UUID guid;

    private TransactionValue(Long amount, TransactionClassify transactionClassify, Account account, UUID guid) {
        this.amount = amount;
        this.transactionClassify = transactionClassify;
        this.account = account;
        this.guid = guid;

    }

    public static TransactionValue from(Long amount, TransactionClassify transactionClassify, Account account, UUID guid) {
        return new TransactionValue(amount, transactionClassify, account, guid);
    }

}
