package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.exception.ServiceUnavailableException;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.TransactionRepository;
import com.depromeet.bank.service.TransactionService;
import com.depromeet.bank.vo.TransactionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    //super user
    private static final Long SUPER_ACCOUNT_ID = 0l;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /*
     * 유저는 여러 계좌를 가질 수 있으므로
     * 계좌-계좌의 거래
     * */
    @Override
    @Transactional
    public void createTransaction(TransactionRequest transactionRequest, Long memberId) {

        Account fromAccount = accountRepository.findById(transactionRequest.getFromAccountId())
                .orElseThrow(() -> new NotFoundException("계좌가 존재하지 않습니다."));

        if (fromAccount.getMember().getId() != memberId) {
            throw new ServiceUnavailableException("접근 권한이 없습니다.");
        }

        Account toAccount = accountRepository.findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new NotFoundException("계좌가 존재하지 않습니다."));

        String guid = UUID.randomUUID().toString();
        Long amount = transactionRequest.getAmount();

        TransactionValue fromTransactionValue =
                TransactionValue.of(amount, TransactionClassify.DEPOSIT, fromAccount, guid);
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        transactionRepository.save(Transaction.from(fromTransactionValue));


        TransactionValue toTransactionValue =
                TransactionValue.of(amount, TransactionClassify.WITHDRAWAL, toAccount, guid);
        toAccount.setBalance(toAccount.getBalance() + amount);
        transactionRepository.save(Transaction.from(toTransactionValue));


    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransaction(Long accountId, int page) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("계좌가 존재하지 않습니다."));

        Pageable pageable = PageRequest.of(page, 10);
        return transactionRepository.findAllByAccount_Id(accountId, pageable)
                .stream()
                .collect(Collectors.toList()).stream()
                .map(TransactionResponse::from)
                .collect(Collectors.toList());
    }


    /*
     * 해당하는 guid를 가지고 있는 거래 내역들을 찾아
     * 그 거래내역을 가지고 있는 계좌로 부터 돈을 돌려주고
     * 취소한 거래 내역을 추가한다
     * */
    @Override
    @Transactional
    public void deleteTransaction(UUID guid) {
        transactionRepository.findByGuid(guid).ifPresent(
                s -> s.forEach(t -> {
                    String cancelGuid = UUID.randomUUID().toString();
                    accountRepository.findById(t.getAccount()
                            .getId())
                            .ifPresent(account -> {
                                if (t.getTransactionClassify() == TransactionClassify.DEPOSIT) {

                                    TransactionValue fromTransactionValue =
                                            TransactionValue.of(t.getAmount(), TransactionClassify.WITHDRAWAL, account, cancelGuid);
                                    account.setBalance(account.getBalance() - t.getAmount());
                                    transactionRepository.save(Transaction.from(fromTransactionValue));


                                } else if (t.getTransactionClassify() == TransactionClassify.WITHDRAWAL) {

                                    TransactionValue fromTransactionValue =
                                            TransactionValue.of(t.getAmount(), TransactionClassify.DEPOSIT, account, cancelGuid);
                                    account.setBalance(account.getBalance() + t.getAmount());
                                    transactionRepository.save(Transaction.from(fromTransactionValue));

                                }
                            });

                })

        );

    }


}
