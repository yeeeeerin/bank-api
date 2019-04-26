package com.depromeet.bank.domain.account;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AccountType {
    SYSTEM(1),
    MEMBER(2),
    INSTRUMENT(3),
    UNKNOWN(null);

    private static final Map<Integer, AccountType> accountTypeMap;

    static {
        accountTypeMap = Stream.of(AccountType.values())
                .collect(Collectors.toMap(AccountType::getValue, type -> type));
    }

    private final Integer value;

    AccountType(Integer value) {
        this.value = value;
    }

    public static AccountType from(Integer value) {
        return accountTypeMap.getOrDefault(value, AccountType.UNKNOWN);
    }

    public Integer getValue() {
        return value;
    }
}
