package com.depromeet.bank.domain.instrument;

import com.depromeet.bank.domain.rule.AdjustmentRuleFactory;
import com.depromeet.bank.vo.AdjustmentRuleValue;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InstrumentFactory {
    private final AdjustmentRuleFactory adjustmentRuleFactory;

    public Instrument createInstrument(InstrumentValue instrumentValue,
                                       List<AdjustmentRuleValue> adjustmentRuleValues) {
        Instrument instrument = Instrument.from(instrumentValue);
        adjustmentRuleValues.stream()
                .map(adjustmentRuleFactory::createAdjustmentRule)
                .forEach(instrument::addAdjustmentRule);
        return instrument;
    }
}
