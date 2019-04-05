package com.depromeet.bank.domain.rule;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum NotType {
    POSITIVE(1),
    NEGATIVE(2),
    UNKNOWN(null);

    private static final Map<Integer, NotType> notTypeMap;

    static {
        notTypeMap = Stream.of(NotType.values())
                .collect(Collectors.toMap(NotType::getValue, type -> type));
    }

    private final Integer value;

    NotType(Integer value) {
        this.value = value;
    }

    public static NotType from(Integer value) {
        return notTypeMap.getOrDefault(value, NotType.UNKNOWN);
    }
}
