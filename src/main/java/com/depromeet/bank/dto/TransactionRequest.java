package com.depromeet.bank.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

import static com.depromeet.bank.domain.account.Account.SYSTEM_ACCOUNT_ID;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private Long amount;

    private TransactionRequest(Long fromAccountId,
                               Long toAccountId,
                               Long amount) {
        this.fromAccountId = Objects.requireNonNull(fromAccountId);
        this.toAccountId = Objects.requireNonNull(toAccountId);
        this.amount = Objects.requireNonNull(amount);
    }

    public static TransactionRequest forSettlement(Long toAccountId, Long amount, Double rate) {
        return new TransactionRequest(
                SYSTEM_ACCOUNT_ID,
                toAccountId,
                Double.valueOf(amount * rate).longValue()
        );
    }

    public static TransactionRequest forInstrument(Long fromAccountId, Long toAccountId, Long investment) {
        return new TransactionRequest(
                fromAccountId,
                toAccountId,
                investment
        );
    }
}
