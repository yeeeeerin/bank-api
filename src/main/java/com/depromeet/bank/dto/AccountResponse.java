package com.depromeet.bank.dto;

import com.depromeet.bank.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Builder
@Getter
public class AccountResponse {

    private Long id;
    private String name;
    private Long balance;
    private Double rate;
    private LocalDateTime createdAt;

    public static AccountResponse from(Account account) {
        Assert.notNull(account, "'account' must not be null");

        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .rate(account.getRate())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .build();
    }

}
