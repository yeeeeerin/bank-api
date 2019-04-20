package com.depromeet.bank.scheduler;

import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.service.AirInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AIrInfoScheduler implements Fetchable {
    private final AirInfoService airInfoService;

    @Override
    @Scheduled(cron = "0 0 */4 * * *")
    public void fetch() {
        airInfoService.createAirInfoByStationName(OpenApiStationName.SEOUL);
    }
}
