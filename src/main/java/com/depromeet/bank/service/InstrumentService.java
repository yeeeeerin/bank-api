package com.depromeet.bank.service;

import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.dto.InstrumentExpirationType;
import com.depromeet.bank.vo.AdjustmentRuleValue;
import com.depromeet.bank.vo.InstrumentValue;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InstrumentService {
    List<Instrument> getInstruments(Pageable pageable, InstrumentExpirationType expirationType);

    List<Instrument> getInstrumentsNeedToBeSettled(LocalDateTime localDateTime);

    Optional<Instrument> getInstrument(Long instrumentId);

    Instrument createInstrument(InstrumentValue instrumentValue, List<AdjustmentRuleValue> adjustmentRuleValues);

    Instrument joinInstrument(Long memberId, Long instrumentId, Long investment);

    Instrument updateInstrument(Long instrumentId, InstrumentValue instrumentValue);

    void deleteInstrument(Long instrumentId);
}
