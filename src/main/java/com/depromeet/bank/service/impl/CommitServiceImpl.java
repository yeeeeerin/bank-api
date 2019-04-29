package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.data.git.Commit;
import com.depromeet.bank.git.CommitParser;
import com.depromeet.bank.git.GitAccount;
import com.depromeet.bank.repository.CommitRepository;
import com.depromeet.bank.repository.GitAccountRepository;
import com.depromeet.bank.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {

    private final CommitParser commitParser;
    private final CommitRepository commitRepository;
    private final GitAccountRepository gitAccountRepository;

    @Override
    public void createCommitCount() {
        int commitCount = Stream.of(GitAccount.values())
                .mapToInt(account -> commitParser.parser(account.getUrl()))
                .sum();

        Commit commit = new Commit();
        commit.setCommitCount(commitCount);
        commitRepository.save(commit);
    }

    /*
     * 데이터베이스로부터 account가져와 commit수 체크하는 것
     * */
    public void createCommitCountFromDB() {
        int commitCount = gitAccountRepository.findAll().stream()
                .map(a -> a.getUrl())
                .mapToInt(commitParser::parser)
                .sum();

        Commit commit = new Commit();
        commit.setCommitCount(commitCount);
        commitRepository.save(commit);
    }
}
