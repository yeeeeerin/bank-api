package com.depromeet.bank.domain.account;

import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.naming.AdjectiveName;
import com.depromeet.bank.domain.account.naming.NounName;
import com.depromeet.bank.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class AccountFactory {

    private static final String CHARSET = "9876543210";
    private static final double RATE_DEFAULT = 0.0;

    public Account setAccount(Member member, AccountDto accountDto) {

        Double rate = Optional.ofNullable(accountDto.getRate()).orElse(RATE_DEFAULT);

        return Account.builder()
                .member(member)
                .name(naming(accountDto.getName()))
                .accountNumber(createAccountNumber())
                .balance(0L)
                .rate(rate)
                .build();
    }

    public Account createInstrumentAccount(Member member, Instrument instrument, Long investment) {
        Double rate = RATE_DEFAULT;
        return Account.builder()
                .member(member)
                .name(instrument.getName())
                .accountNumber(createAccountNumber())
                .balance(investment)
                .rate(rate)
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
        int j = (int) Math.ceil(Math.log(num) / Math.log(CHARSET.length()));
        if (num % 91 == 0 || num == 1) {
            j = j + 1;
        }
        for (int i = 0; i < j; i++) {
            encodedString.append(CHARSET.charAt((int) (num % CHARSET.length())));
            num /= CHARSET.length();
        }
        return encodedString.toString();
    }

}
