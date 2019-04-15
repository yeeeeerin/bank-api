package com.depromeet.bank.scheduler;

import com.depromeet.bank.service.InstrumentService;
import com.depromeet.bank.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettlementScheduler {

    private final InstrumentService instrumentService;
    private final SettlementService settlementService;

    @Scheduled(cron = "0 0 0 * * *")
    public void settle() {
        instrumentService.getInstrumentsExpiredAndIncomplete(LocalDateTime.now())
                .stream()
                .peek(instrument -> log.warn(instrument.toString()))
                .map(instrument -> settlementService.updateRuleIsSatisfied(instrument.getInstrumentId()))
                .forEach(instrument -> settlementService.settle(instrument.getInstrumentId()));
    }
}
