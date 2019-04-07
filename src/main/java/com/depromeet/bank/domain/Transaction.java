package com.depromeet.bank.domain;

import com.depromeet.bank.vo.TransactionValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private UUID guid;

    private Long amount;

    @CreatedDate
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactionClassify transactionClassify;

    @ManyToOne
    private Account account;

    private Transaction(Long amount, TransactionClassify transactionClassify, Account account) {
        this.amount = amount;
        this.transactionClassify = transactionClassify;
        this.account = account;
    }

    public static Transaction from(TransactionValue transactionValue) {
        Assert.notNull(transactionValue, "'transactionValue' must not be null");

        Long amount = transactionValue.getAmount();
        TransactionClassify transactionClassify = transactionValue.getTransactionClassify();
        Account account = transactionValue.getAccount();

        return new Transaction(amount, transactionClassify, account);
    }

}
