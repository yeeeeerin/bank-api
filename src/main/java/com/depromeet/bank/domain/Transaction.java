package com.depromeet.bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private int amount;

    private LocalDateTime dateTime;

    //이넘으로 거래가 출금인지 입금인지 구분하기

    @ManyToOne
    private Account account;

}
