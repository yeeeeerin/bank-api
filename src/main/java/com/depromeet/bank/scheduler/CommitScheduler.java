package com.depromeet.bank.scheduler;


import com.depromeet.bank.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommitScheduler implements Fetchable {

    private final CommitService commitService;

    @Override
    @Scheduled(cron = "0 58 23 * * *")
    public void fetch() {
        commitService.createCommitCount();
    }
}
