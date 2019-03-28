package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.AccountDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createAccount(AccountDto accountDto, Long memberId) throws IllegalAccessException {

        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(accountDto, "'accountDto' must not be null");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalAccessException("회원이 존재하지 않음"));

        Account account = Account.builder()
                .member(member)
                .name(accountDto.getName())
                .balance(0)
                .rate(accountDto.getRate())
                .build();

        accountRepository.save(account);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccount(Long memberId, int page) throws IllegalAccessException {

        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(page, "'page' must not be null");

        memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalAccessException("회원이 존재하지 않음"));

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

}
