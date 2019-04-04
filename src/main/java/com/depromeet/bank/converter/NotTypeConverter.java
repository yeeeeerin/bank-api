package com.depromeet.bank.converter;

import com.depromeet.bank.domain.rule.NotType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NotTypeConverter implements AttributeConverter<NotType, String> {

    @Override
    public String convertToDatabaseColumn(NotType attribute) {
        return attribute.name();
    }

    @Override
    public NotType convertToEntityAttribute(String dbData) {
        return NotType.valueOf(dbData);
    }
}
