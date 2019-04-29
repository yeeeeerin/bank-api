package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.data.git.Commit;
import com.depromeet.bank.git.CommitParser;
import com.depromeet.bank.git.GitAccountParser;
import com.depromeet.bank.repository.CommitRepository;
import com.depromeet.bank.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitServiceImpl implements CommitService {

    private final CommitParser commitParser;
    private final GitAccountParser gitAccountParser;
    private final CommitRepository commitRepository;

    public void createCommitCount() {
        int commitCount = gitAccountParser.accountParser().stream()
                .mapToInt(commitParser::parser)
                .sum();

        Commit commit = new Commit();
        commit.setCommitCount(commitCount);
        commitRepository.save(commit);
    }
}
