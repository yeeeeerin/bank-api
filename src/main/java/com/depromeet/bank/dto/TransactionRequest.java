package com.depromeet.bank.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransactionRequest {
    Long fromAccountId;
    Long toAccountId;

    Long amount;
}
