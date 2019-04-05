package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.exception.UnauthorizedException;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.TransactionRepository;
import com.depromeet.bank.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

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
        member.setId(1l);
        fromAccount = TestHelper.createAccount("", 10000l, 0.0, member);
        fromAccount.setId(1l);
        toAccount = TestHelper.createAccount("", 10000l, 0.0, member);
        toAccount.setId(2l);

        transactionRequest = TestHelper.createTransactionRequest(fromAccount.getId(), toAccount.getId(), 2000l);
    }

    @Test(expected = NotFoundException.class)
    public void 이체시_계좌가_없으면_NotFoundException() {
        //given

        //when
        transactionService.createTransaction(member.getId(), transactionRequest);

        //then


    }

    @Test(expected = NotFoundException.class)
    public void 이체_내역들을_가져올때_계좌가_없으면_NotFoundException() {
        //given

        //when
        transactionService.getTransaction(1l, 1l, 0);

        //then


    }

    @Test(expected = UnauthorizedException.class)
    public void 이체시_권한이_없다면_ServiceUnavailableException() {

        //given
        when(accountRepository.findById(transactionRequest.getFromAccountId())).thenReturn(Optional.ofNullable(fromAccount));

        //when
        transactionService.createTransaction(2l, transactionRequest);

        //then


    }

    @Test
    public void 이체시_권한이_있다면_Pass() {

        //given
        when(accountRepository.findById(transactionRequest.getFromAccountId()))
                .thenReturn(Optional.ofNullable(fromAccount));
        when(accountRepository.findById(transactionRequest.getToAccountId()))
                .thenReturn(Optional.ofNullable(toAccount));

        //when
        transactionService.createTransaction(1l, transactionRequest);

        //then


    }

}