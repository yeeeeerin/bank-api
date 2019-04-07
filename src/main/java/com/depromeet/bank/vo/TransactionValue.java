package com.depromeet.bank.vo;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.TransactionClassify;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@ToString
@Value(staticConstructor = "of")
public class TransactionValue {

    private Long amount;
    private TransactionClassify transactionClassify;
    private Account account;
    private UUID guid;

}
