package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.data.git.Commit;
import com.depromeet.bank.git.CommitParser;
import com.depromeet.bank.git.GitAccounts;
import com.depromeet.bank.repository.CommitRepository;
import com.depromeet.bank.repository.GitAccountRepository;
import com.depromeet.bank.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {

    private final CommitParser commitParser;
    private final CommitRepository commitRepository;
    private final GitAccountRepository gitAccountRepository;

    @Override
    @Transactional
    public void createCommitCount() {
        int commitCount = Stream.of(GitAccounts.values())
                .mapToInt(account -> commitParser.parse(account.getUrl()))
                .sum();

        Commit commit = Commit.builder()
                .commitCount(commitCount)
                .build();
        commitRepository.save(commit);
    }

    /*
     * 데이터베이스로부터 account가져와 commit수 체크하는 것
     * */
    @Override
    @Transactional
    public void createCommitCountFromDB() {
        int commitCount = gitAccountRepository.findAll().stream()
                .map(a -> a.getUrl())
                .mapToInt(commitParser::parse)
                .sum();

        Commit commit = Commit.builder()
                .commitCount(commitCount)
                .build();
        commitRepository.save(commit);
    }
}
