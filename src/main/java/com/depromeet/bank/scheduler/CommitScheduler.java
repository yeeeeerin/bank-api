package com.depromeet.bank.scheduler;


import com.depromeet.bank.git.CommitParser;
import com.depromeet.bank.git.GitAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CommitScheduler implements Fetchable {

    private final CommitParser parser;

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void fetch() {

        int commitSum = Arrays.stream(GitAccount.values())
                .mapToInt(account -> {
                    try {
                        return parser.parser(account.getUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }).sum();


    }
}
