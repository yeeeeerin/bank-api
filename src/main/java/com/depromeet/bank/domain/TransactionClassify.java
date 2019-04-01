package com.depromeet.bank.domain;

public enum TransactionClassify {

    DEPOSIT("입금"), WITHDRAWAL("출금");

    String name;

    TransactionClassify(String name) {
        this.name = name;
    }

}
