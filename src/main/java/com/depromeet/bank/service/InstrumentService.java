package com.depromeet.bank.service;

import com.depromeet.bank.domain.Instrument;
import com.depromeet.bank.vo.InstrumentValue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InstrumentService {
    List<Instrument> getInstruments(Pageable pageable);
    Optional<Instrument> getInstrument(Long instrumentId);
    Instrument createInstrument(InstrumentValue instrumentValue);
    Instrument updateInstrument(Long instrumentId, InstrumentValue instrumentValue);
    void deleteInstrument(Long instrumentId);
}
