package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.TransactionRepository;
import com.depromeet.bank.service.TransactionService;
import com.depromeet.bank.vo.TransactionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    //super user
    private static final Long SUPER_ACCOUNT = 0l;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /*
     * 유저는 여러 계좌를 가질 수 있다.
     * 계좌-계좌의 거래
     * */
    @Override
    @Transactional
    public void createTransaction(TransactionRequest transactionRequest) {

        Account fromAccount = accountRepository.findById(transactionRequest.getFromAccountId())
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        Account toAccount = accountRepository.findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        UUID guid = UUID.randomUUID();
        Long amount = transactionRequest.getAmount();

        TransactionValue fromTransactionValue =
                TransactionValue.from(amount, TransactionClassify.DEPOSIT, fromAccount, guid);
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        transactionRepository.save(Transaction.from(fromTransactionValue));


        TransactionValue toTransactionValue =
                TransactionValue.from(amount, TransactionClassify.WITHDRAWAL, toAccount, guid);
        fromAccount.setBalance(fromAccount.getBalance() + amount);
        transactionRepository.save(Transaction.from(toTransactionValue));

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Transaction>> getTransaction(Long accountId) {
        Account fromAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("계정이 존재하지 않습니다."));

        return Optional.of(new ArrayList<>(fromAccount.getTransactions()));
    }


    @Override
    @Transactional
    public void deleteTransaction(UUID guid) {
        transactionRepository.findByGuid(guid).ifPresent(
                s -> s.forEach(transactionRepository::delete)
        );

    }

}
