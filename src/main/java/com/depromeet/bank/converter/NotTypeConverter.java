package com.depromeet.bank.converter;

import com.depromeet.bank.domain.rule.NotType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NotTypeConverter implements AttributeConverter<NotType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotType attribute) {
        return attribute.getValue();
    }

    @Override
    public NotType convertToEntityAttribute(Integer dbData) {
        return NotType.from(dbData);
    }
}
