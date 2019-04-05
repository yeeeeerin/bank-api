package com.depromeet.bank.domain.rule;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ComparisonType {
    GREATER_THAN(1),
    GREATER_THAN_OR_EQUAL_TO(2),
    EQUAL_TO(3),
    LESS_THAN_OR_EQUAL_TO(4),
    LESS_THAN(5),
    UNKNOWN(null);

    private static final Map<Integer, ComparisonType> comparisonTypeMap;

    static {
        comparisonTypeMap = Stream.of(ComparisonType.values())
                .collect(Collectors.toMap(ComparisonType::getValue, type -> type));
    }

    private final Integer value;

    ComparisonType(Integer value) {
        this.value = value;
    }

    public static ComparisonType from(Integer value) {
        return comparisonTypeMap.getOrDefault(value, ComparisonType.UNKNOWN);
    }
}
