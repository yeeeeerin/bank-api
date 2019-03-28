package com.depromeet.bank.service;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.dto.AccountDto;

import java.util.List;

public interface AccountService {

    void createAccount(AccountDto accountDto, Long memberId) throws IllegalAccessException;

    List<Account> getAccount(Long memberId, int page) throws IllegalAccessException;

    void deleteAccount(Long accountId);

}
