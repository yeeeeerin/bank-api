package com.depromeet.bank.service;

import com.depromeet.bank.dto.AccountDto;

public interface AccountService {

    void createAccount(AccountDto accountDto, Long memberId) throws IllegalAccessException;

}
