package com.depromeet.bank.converter;

import com.depromeet.bank.domain.rule.ComparisonType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ComparisonTypeConverter implements AttributeConverter<ComparisonType, String> {
    @Override
    public String convertToDatabaseColumn(ComparisonType attribute) {
        return attribute.name();
    }

    @Override
    public ComparisonType convertToEntityAttribute(String dbData) {
        return ComparisonType.valueOf(dbData);
    }
}
