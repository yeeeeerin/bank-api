package com.depromeet.bank.domain;

import com.depromeet.bank.domain.account.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    private Long balance;

    private String name;

    @CreatedDate
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactionClassify transactionClassify;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Transaction(Long amount,
                        TransactionClassify transactionClassify,
                        Account account,
                        String guid,
                        Long balance,
                        String name) {
        this.amount = amount;
        this.transactionClassify = transactionClassify;
        this.account = account;
        this.guid = guid;
        this.balance = balance;
        this.name = name;
    }

    public static Transaction of(Long amount,
                                 TransactionClassify transactionClassify,
                                 Account account,
                                 String guid,
                                 Long balance,
                                 String name) {
        return new Transaction(
                amount,
                transactionClassify,
                account,
                guid,
                balance,
                name
        );
    }

}
