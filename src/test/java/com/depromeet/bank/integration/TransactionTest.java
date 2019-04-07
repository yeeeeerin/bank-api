package com.depromeet.bank.integration;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.repository.TransactionRepository;
import com.depromeet.bank.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TransactionTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    private Member member;
    private Account fromAccount;
    private Account toAccount;
    private TransactionRequest transactionRequest;

    @Before
    public void setup() {
        member = TestHelper.createMember(1l, "yerin", "http://test.png");
        memberRepository.save(member);

        fromAccount = TestHelper.createAccount("11", 1000l, 0.0, member);
        accountRepository.save(fromAccount);

        toAccount = TestHelper.createAccount("22", 1000l, 0.0, member);
        accountRepository.save(toAccount);

        transactionRequest = TestHelper.createTransactionRequest(fromAccount.getId(), toAccount.getId(), 200l);
    }

    @Test
    public void 이체가_성공하면_요청계좌에는_800원_받는계좌에는_1200원이_있다() {

        //given
        //when
        transactionService.createTransaction(member.getId(), transactionRequest);

        Account 요청계좌 = accountRepository.findById(fromAccount.getId()).orElseThrow(NotFoundException::new);
        Account 받는계좌 = accountRepository.findById(toAccount.getId()).orElseThrow(NotFoundException::new);

        //then
        assertThat(요청계좌.getBalance(), is(800l));
        assertThat(받는계좌.getBalance(), is(1200l));
    }


}
