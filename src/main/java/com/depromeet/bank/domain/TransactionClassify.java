package com.depromeet.bank.domain;

import lombok.Getter;

@Getter
public enum TransactionClassify {

    DEPOSIT("입금"), WITHDRAWAL("출금");

    private final String value;

    TransactionClassify(String value) {
        this.value = value;
    }

}
