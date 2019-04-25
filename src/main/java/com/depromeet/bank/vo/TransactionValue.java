package com.depromeet.bank.vo;

import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.TransactionClassify;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;

@ToString
@Value(staticConstructor = "of")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionValue {

    private Long amount;
    private LocalDateTime dateTime;
    private TransactionClassify transactionClassify;
    private Account account;
    private String guid;
    private Long balance;

    public static TransactionValue from(Transaction transaction) {
        return new TransactionValue(
                transaction.getAmount(),
                transaction.getDateTime(),
                transaction.getTransactionClassify(),
                transaction.getAccount(),
                transaction.getGuid(),
                transaction.getBalance()
        );
    }
}
