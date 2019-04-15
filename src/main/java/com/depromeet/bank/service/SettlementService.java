package com.depromeet.bank.service;

import com.depromeet.bank.domain.instrument.Instrument;

public interface SettlementService {
    Instrument updateRuleIsSatisfied(Long instrumentId);
    void settle(Long instrumentId);
}
