package com.depromeet.bank.controller;

import com.depromeet.bank.service.InstrumentService;
import com.depromeet.bank.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettlementController {
    private final InstrumentService instrumentService;
    private final SettlementService settlementService;

    @PostMapping("/instruments/settle")
    public void settle() {
        instrumentService.getInstrumentsExpiredAndIncomplete(LocalDateTime.now())
                .stream()
                .map(instrument -> settlementService.updateRuleIsSatisfied(instrument.getInstrumentId()))
                .forEach(instrument -> settlementService.settle(instrument.getInstrumentId()));
    }
}
