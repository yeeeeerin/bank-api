package com.depromeet.bank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccountDto {

    private String name;
    private Double rate;

    private AccountDto(Double rate) {
        this.rate = rate;
    }

    public static AccountDto initAccount() {
        return new AccountDto(0.0);
    }

}
