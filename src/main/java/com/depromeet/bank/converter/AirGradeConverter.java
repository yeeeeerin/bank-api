package com.depromeet.bank.converter;

import com.depromeet.bank.adapter.openapi.AirGrade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AirGradeConverter implements AttributeConverter<AirGrade, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AirGrade attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AirGrade convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return AirGrade.from(dbData);
    }
}
