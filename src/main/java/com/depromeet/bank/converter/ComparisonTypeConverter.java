package com.depromeet.bank.converter;

import com.depromeet.bank.domain.rule.ComparisonType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ComparisonTypeConverter implements AttributeConverter<ComparisonType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ComparisonType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ComparisonType convertToEntityAttribute(Integer dbData) {
        return ComparisonType.from(dbData);
    }
}
