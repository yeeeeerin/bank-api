package com.depromeet.bank.scheduler;

import com.depromeet.bank.domain.data.attendance.DepromeetSessionType;
import com.depromeet.bank.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AttendanceScheduler implements Fetchable {
    private final AttendanceService attendanceService;

    @Override
    @Scheduled(cron = "0 0 6 * * *")
    public void fetch() {
        DepromeetSessionType latestSession = DepromeetSessionType.latest(LocalDateTime.now());
        attendanceService.fetch(latestSession);
    }
}
