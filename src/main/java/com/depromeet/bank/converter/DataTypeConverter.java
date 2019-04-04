package com.depromeet.bank.converter;

import com.depromeet.bank.domain.rule.DataType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DataTypeConverter implements AttributeConverter<DataType, String> {
    @Override
    public String convertToDatabaseColumn(DataType attribute) {
        return attribute.name();
    }

    @Override
    public DataType convertToEntityAttribute(String dbData) {
        return DataType.valueOf(dbData);
    }
}
