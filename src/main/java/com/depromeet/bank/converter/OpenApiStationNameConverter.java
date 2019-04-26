package com.depromeet.bank.converter;

import com.depromeet.bank.adapter.openapi.OpenApiStationName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OpenApiStationNameConverter implements AttributeConverter<OpenApiStationName, String> {
    @Override
    public String convertToDatabaseColumn(OpenApiStationName attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public OpenApiStationName convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OpenApiStationName.from(dbData);
    }
}
