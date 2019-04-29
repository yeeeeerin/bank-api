package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.data.git.GitAccount;
import com.depromeet.bank.dto.GitAccountRequest;
import com.depromeet.bank.repository.GitAccountRepository;
import com.depromeet.bank.service.GitAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitAccountServiceImpl implements GitAccountService {

    private final GitAccountRepository accountRepository;

    @Override
    public void saveAccount(GitAccountRequest gitAccountRequest) {
        GitAccount gitAccount = new GitAccount();
        gitAccount.setUrl(gitAccountRequest.getUrl());
        accountRepository.save(gitAccount);
    }

}
