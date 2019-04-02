package com.depromeet.bank.utils;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.naming.AdjectiveName;
import com.depromeet.bank.domain.naming.NounName;
import com.depromeet.bank.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class AccountFactory {

    private static final String charSet = "9876543210";

    public Account setAccount(Member member, AccountDto accountDto) {
        return Account.builder()
                .member(member)
                .name(naming(accountDto.getName()))
                .accountNumber(createAccountNumber())
                .balance(0L)
                .rate(accountDto.getRate())
                .build();
    }


    private String naming(String name) {

        return Optional.ofNullable(name)
                .filter(s -> !s.isEmpty())
                .orElse(AdjectiveName.getRandom() + " " + NounName.getRandom());
    }

    private String createAccountNumber() {
        UUID uuid = UUID.randomUUID();
        String accountNumber = encode(uuid.getMostSignificantBits());
        log.info("id : " + accountNumber);
        return accountNumber;
    }

    private String encode(long num) {
        num = Math.abs(num);
        StringBuilder encodedString = new StringBuilder();
        int j = (int) Math.ceil(Math.log(num) / Math.log(charSet.length()));
        if (num % 91 == 0 || num == 1) {
            j = j + 1;
        }
        for (int i = 0; i < j; i++) {
            encodedString.append(charSet.charAt((int) (num % charSet.length())));
            num /= charSet.length();
        }
        return encodedString.toString();
    }

}
