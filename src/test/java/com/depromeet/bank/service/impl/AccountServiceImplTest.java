package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AccountServiceImplTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountService accountService;

    private Member member;
    private Account account;

    @Before
    public void setup() {
        member = TestHelper.createMember(1L, "yerin", "http://test.png");
        memberRepository.save(member);

        for (int i = 0; i < 20; i++) {
            account = TestHelper.createAccount("하나계좌", 0L, 0.0, member);
            accountRepository.save(account);
        }
        log.info("date : " + String.valueOf(account.getCreatedAt()));

    }


    @Test
    public void 계좌_페이징_테스트() {
        //given

        //when
        List<Account> accountList = accountService.getAccount(member.getId(), 0);

        //then
        assertThat(accountList.size(), is(10));

    }
}