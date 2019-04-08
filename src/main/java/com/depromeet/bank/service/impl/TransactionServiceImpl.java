package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.exception.ServiceUnavailableException;
import com.depromeet.bank.exception.UnauthorizedException;
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
    public void createTransaction(Long memberId, TransactionRequest transactionRequest) {

        Account fromAccount = accountRepository.findById(transactionRequest.getFromAccountId())
                .orElseThrow(() -> new NotFoundException(""));

        if (fromAccount.getMember().getId() != memberId) {
            throw new UnauthorizedException();
        }
        if (fromAccount.getBalance() <= transactionRequest.getAmount()) {
            throw new ServiceUnavailableException("There is not enough balance.");
        }

        Account toAccount = accountRepository.findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new NotFoundException("Not found account"));

        String guid = UUID.randomUUID().toString();
        Long amount = transactionRequest.getAmount();

        TransactionValue fromTransactionValue =
                TransactionValue.of(amount, TransactionClassify.WITHDRAWAL, fromAccount, guid);
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        transactionRepository.save(Transaction.from(fromTransactionValue));


        TransactionValue toTransactionValue =
                TransactionValue.of(amount, TransactionClassify.DEPOSIT, toAccount, guid);
        toAccount.setBalance(toAccount.getBalance() + amount);
        transactionRepository.save(Transaction.from(toTransactionValue));


    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransaction(Long memberId, Long accountId, int page) {
        Long accountMemberId = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Not found account"))
                .getMember()
                .getId();

        if (accountMemberId != memberId) {
            throw new UnauthorizedException();
        }

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
