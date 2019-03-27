package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Instrument;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.InstrumentRepository;
import com.depromeet.bank.service.InstrumentService;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepository instrumentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Instrument> getInstruments(Pageable pageable) {
        return instrumentRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Instrument> getInstrument(Long instrumentId) {
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        return instrumentRepository.findById(instrumentId);
    }

    @Override
    @Transactional
    public Instrument createInstrument(InstrumentValue instrumentValue) {
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        Instrument instrument = Instrument.from(instrumentValue);
        return instrumentRepository.save(instrument);
    }

    @Override
    @Transactional
    public Instrument updateInstrument(Long instrumentId, InstrumentValue instrumentValue) {
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new NotFoundException("상품이 없습니다. "));
        return instrument.update(instrumentValue);
    }

    @Override
    @Transactional
    public void deleteInstrument(Long instrumentId) {
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        instrumentRepository.findById(instrumentId)
                .ifPresent(instrumentRepository::delete);
    }
}
