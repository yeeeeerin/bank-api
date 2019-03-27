package com.depromeet.bank.domain;

public enum TransactClassify {

    DEPOSIT("입급"), WITHDRAWAL("출금");

    String name;

    TransactClassify(String name) {
        this.name = name;
    }

}
