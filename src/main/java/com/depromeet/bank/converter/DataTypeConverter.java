package com.depromeet.bank.converter;

import com.depromeet.bank.domain.rule.DataType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DataTypeConverter implements AttributeConverter<DataType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DataType attribute) {
        return attribute.getValue();
    }

    @Override
    public DataType convertToEntityAttribute(Integer dbData) {
        return DataType.from(dbData);
    }
}
