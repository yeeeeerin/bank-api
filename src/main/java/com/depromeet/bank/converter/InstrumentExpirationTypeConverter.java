package com.depromeet.bank.converter;

import com.depromeet.bank.dto.InstrumentExpirationType;
import org.springframework.core.convert.converter.Converter;

public class InstrumentExpirationTypeConverter implements Converter<String, InstrumentExpirationType> {
    @Override
    public InstrumentExpirationType convert(String source) {
        return InstrumentExpirationType.from(source);
    }
}
