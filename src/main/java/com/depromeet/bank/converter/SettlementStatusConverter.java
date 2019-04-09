package com.depromeet.bank.converter;

import com.depromeet.bank.domain.instrument.SettlementStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SettlementStatusConverter implements AttributeConverter<SettlementStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SettlementStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public SettlementStatus convertToEntityAttribute(Integer dbData) {
        return SettlementStatus.from(dbData);
    }
}
