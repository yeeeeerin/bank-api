package com.depromeet.bank.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Integer amount;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactClassify transactClassify;

    @ManyToOne
    private Account account;

}
