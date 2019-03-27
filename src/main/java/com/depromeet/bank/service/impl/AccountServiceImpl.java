package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createAccount(AccountDto accountDto, Long memberId) throws IllegalAccessException {

        //member가 존재하는지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalAccessException("회원이 존재하지 않음"));

        Account account = new Account();
        account.setMember(member);
        account.setName(accountDto.getName());
        account.setBalance(0);
        account.setRate(0.0);

        accountRepository.save(account);

    }

}
