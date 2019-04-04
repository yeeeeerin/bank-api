package com.depromeet.bank.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.concurrent.TimeUnit;

@Converter
public class TimeUnitConverter implements AttributeConverter<TimeUnit, String> {
    @Override
    public String convertToDatabaseColumn(TimeUnit attribute) {
        return attribute.name();
    }

    @Override
    public TimeUnit convertToEntityAttribute(String dbData) {
        return TimeUnit.valueOf(dbData);
    }
}
