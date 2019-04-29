package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.data.git.Commit;
import com.depromeet.bank.git.CommitParser;
import com.depromeet.bank.git.GitAccount;
import com.depromeet.bank.repository.CommitRepository;
import com.depromeet.bank.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {

    private final CommitParser commitParser;
    private final CommitRepository commitRepository;

    @Override
    public void createCommitCount() {
        int commitCount = Stream.of(GitAccount.values())
                .mapToInt(account -> commitParser.parser(account.getUrl()))
                .sum();

        Commit commit = new Commit();
        commit.setCommitCount(commitCount);
        commitRepository.save(commit);
    }
}
