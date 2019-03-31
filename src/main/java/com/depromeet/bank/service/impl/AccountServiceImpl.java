package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.naming.AdjectiveName;
import com.depromeet.bank.domain.naming.NounName;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final String charSet = "9876543210";

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Optional<Account> createAccount(AccountDto accountDto, Long memberId) {

        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(accountDto, "'accountDto' must not be null");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다"));

        Account account = Account.builder()
                .member(member)
                .name(naming(accountDto.getName()))
                .accountNumber(createAccountNumber())
                .balance(0L)
                .rate(accountDto.getRate())
                .build();


        accountRepository.save(account);

        return Optional.of(account);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccount(Long memberId, int page) {

        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(page, "'page' must not be null");

        Pageable pageable = PageRequest.of(page, 10);

        return accountRepository.findAllByMember_Id(memberId, pageable).stream()
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId) {
        Assert.notNull(accountId, "'accountId' must not be null");
        accountRepository.deleteById(accountId);
    }

    private String naming(String name) {

        return Optional.of(name)
                .filter(s -> {
                    if (!s.isEmpty()) {
                        return true;
                    } else {
                        return false;
                    }
                }).orElse(AdjectiveName.getRandom() + " " + NounName.getRandom());
    }

    private String createAccountNumber() {
        UUID uuid = UUID.randomUUID();
        String accountNumber = encode(uuid.getMostSignificantBits());
        log.info("id : " + accountNumber);
        return accountNumber;
    }

    private String encode(long num) {
        num = Math.abs(num);
        String encodedString = "";
        int j = (int) Math.ceil(Math.log(num) / Math.log(charSet.length()));
        if (num % 91 == 0 || num == 1) {
            j = j + 1;
        }
        for (int i = 0; i < j; i++) {
            encodedString += charSet.charAt((int) (num % charSet.length()));
            num /= charSet.length();
        }
        return encodedString;
    }

}
