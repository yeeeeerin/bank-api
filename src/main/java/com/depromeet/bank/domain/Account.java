package com.depromeet.bank.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int balance;

    private LocalDateTime createDateTime;
    private LocalDateTime expireDateTime;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

}
