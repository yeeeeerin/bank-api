package com.depromeet.bank.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Integer amount;

    @CreatedDate
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactClassify transactClassify;

    @ManyToOne
    private Account account;

}
