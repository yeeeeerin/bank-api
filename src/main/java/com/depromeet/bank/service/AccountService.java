package com.depromeet.bank.service;

import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.dto.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> createAccount(AccountDto accountDto, Long memberId);

    List<Account> getAccount(Long memberId, int page);

    void deleteAccount(Long accountId);

}
