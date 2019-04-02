package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.utils.AccountFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final AccountFactory accountFactory;

    @Override
    @Transactional
    public Optional<Account> createAccount(AccountDto accountDto, Long memberId) {

        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(accountDto, "'accountDto' must not be null");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다"));

        Account account = accountFactory.setAccount(member, accountDto);

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


}
