package com.depromeet.bank.converter;

import com.depromeet.bank.domain.account.AccountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccountTypeConverter implements AttributeConverter<AccountType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AccountType convertToEntityAttribute(Integer dbData) {
        return AccountType.from(dbData);
    }
}
