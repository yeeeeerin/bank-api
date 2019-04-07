package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.TransactionRepository;
import com.depromeet.bank.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("NonAsciiCharacters")
public class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;
    private TransactionRequest transactionRequest;
    private Account fromAccount;
    private Account toAccount;
    private Member member;

    @Before
    public void setup() {
        transactionService = new TransactionServiceImpl(accountRepository, transactionRepository);

        member = TestHelper.createMember(1L, "yerin", "http://test.png");
        fromAccount = TestHelper.createAccount("", 10000l, 0.0, member);
        toAccount = TestHelper.createAccount("", 10000l, 0.0, member);
        transactionRequest = TestHelper.createTransactionRequest(fromAccount.getId(), toAccount.getId(), 2000l);
    }

    @Test(expected = NotFoundException.class)
    public void 이체시_계좌가_없으면_NotFoundException() {
        //given
        //when
        //then
        transactionService.createTransaction(transactionRequest);

    }

    @Test(expected = NotFoundException.class)
    public void 이체_내역들을_가져올때_계좌가_없으면_NotFoundException() {
        //given

        //when
        //then
        transactionService.getTransaction(1l, 0);

    }

}