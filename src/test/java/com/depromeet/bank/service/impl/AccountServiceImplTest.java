package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    private AccountService accountService;
    private Member member;
    private Account account;

    @Before
    public void setup() {
        accountService = new AccountServiceImpl(accountRepository, memberRepository);
        member = TestHelper.createMember(1L, "yerin", "http://test.png");
        account = TestHelper.createAccount("하나계좌", 0, 0.0);
    }

    //todo 테스트 성공시키기
    @Test
    public void 계좌_페이징_테스트() throws IllegalAccessException {
        memberRepository.save(member);
        accountRepository.save(account);

        List<Account> accountList = accountService.getAccount(member.getId(), 0);
        assertThat(accountList.size(), is(1));
    }
}