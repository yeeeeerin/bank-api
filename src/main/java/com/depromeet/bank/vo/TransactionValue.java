package com.depromeet.bank.vo;

import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.TransactionClassify;
import lombok.ToString;
import lombok.Value;

@ToString
@Value(staticConstructor = "of")
public class TransactionValue {

    private Long amount;
    private TransactionClassify transactionClassify;
    private Account account;
    private String guid;

}
