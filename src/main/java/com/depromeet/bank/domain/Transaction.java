package com.depromeet.bank.domain;

import com.depromeet.bank.vo.TransactionValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.depromeet.bank.domain.account.Account;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    private String guid;

    private Long amount;

    @CreatedDate
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactionClassify transactionClassify;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Transaction(Long amount, TransactionClassify transactionClassify, Account account, String guid) {
        this.amount = amount;
        this.transactionClassify = transactionClassify;
        this.account = account;
        this.guid = guid;
    }

    public static Transaction from(TransactionValue transactionValue) {
        Assert.notNull(transactionValue, "'transactionValue' must not be null");

        Long amount = transactionValue.getAmount();
        TransactionClassify transactionClassify = transactionValue.getTransactionClassify();
        Account account = transactionValue.getAccount();
        String guid = transactionValue.getGuid();

        return new Transaction(amount, transactionClassify, account, guid);
    }

}
