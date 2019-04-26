package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.exception.ServiceUnavailableException;
import com.depromeet.bank.exception.UnauthorizedException;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.TransactionRepository;
import com.depromeet.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /*
     * 유저는 여러 계좌를 가질 수 있으므로
     * 계좌-계좌의 거래
     * */
    @Override
    @Transactional
    public void createTransaction(Long memberId, TransactionRequest transactionRequest) {
        createTransactionWithName(memberId, transactionRequest, "계좌 이체");
    }

    @Override
    @Transactional
    public void createTransaction(Long memberId, TransactionRequest transactionRequest, String name) {
        createTransactionWithName(memberId, transactionRequest, name);
    }

    private void createTransactionWithName(Long memberId, TransactionRequest transactionRequest, String name) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(transactionRequest, "'transactionRequest' must not be null");

        Account fromAccount = accountRepository.findById(transactionRequest.getFromAccountId())
                .orElseThrow(() -> new NotFoundException(""));

        if (!memberId.equals(fromAccount.getMember().getId())) {
            throw new UnauthorizedException();
        }
        if (fromAccount.getBalance() < transactionRequest.getAmount()) {
            throw new ServiceUnavailableException("There is not enough balance.");
        }

        Account toAccount = accountRepository.findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new NotFoundException("Not found account"));

        String guid = UUID.randomUUID().toString();
        Long amount = transactionRequest.getAmount();

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        Transaction withdrawal = Transaction.of(amount, TransactionClassify.WITHDRAWAL, fromAccount, guid, fromAccount.getBalance(), name);
        transactionRepository.save(withdrawal);

        toAccount.setBalance(toAccount.getBalance() + amount);
        Transaction deposit = Transaction.of(amount, TransactionClassify.DEPOSIT, toAccount, guid, toAccount.getBalance(), name);
        transactionRepository.save(deposit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransaction(Long memberId, Long accountId, Pageable pageable) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(accountId, "'accountId' must not be null");

        Long accountMemberId = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Not found account"))
                .getMember()
                .getId();

        if (!memberId.equals(accountMemberId)) {
            throw new UnauthorizedException();
        }

        return transactionRepository.findAllByAccount_Id(accountId, pageable)
                .stream()
                .collect(Collectors.toList());
    }


    /*
     * 해당하는 guid를 가지고 있는 거래 내역들을 찾아
     * 그 거래내역을 가지고 있는 계좌로 부터 돈을 돌려주고
     * 취소한 거래 내역을 추가한다.
     * */
    @Override
    @Transactional
    public void deleteTransaction(Long memberId, UUID guid) {
        transactionRepository.findByGuid(guid).ifPresent(
                s -> s.forEach(t -> {
                    String cancelGuid = UUID.randomUUID().toString();
                    accountRepository.findById(t.getAccount()
                            .getId())
                            .ifPresent(account -> {
                                if (t.getTransactionClassify() == TransactionClassify.DEPOSIT) {
                                    if (account.getBalance() <= t.getAmount()) {
                                        throw new ServiceUnavailableException("There is not enough balance");
                                    }
                                    account.setBalance(account.getBalance() - t.getAmount());
                                    Transaction withdrawal = Transaction.of(t.getAmount(), TransactionClassify.WITHDRAWAL, account, cancelGuid, account.getBalance(), "거래 취소");
                                    transactionRepository.save(withdrawal);
                                } else if (t.getTransactionClassify() == TransactionClassify.WITHDRAWAL) {
                                    account.setBalance(account.getBalance() + t.getAmount());
                                    Transaction deposit = Transaction.of(t.getAmount(), TransactionClassify.DEPOSIT, account, cancelGuid, account.getBalance(), "거래 취소");
                                    transactionRepository.save(deposit);
                                }
                            });

                })

        );

    }


}
