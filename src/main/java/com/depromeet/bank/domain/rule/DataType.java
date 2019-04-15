package com.depromeet.bank.domain.rule;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum DataType {
    NUMBER_OF_ATTENDEE(1),
    UNKNOWN(null);

    private static final Map<Integer, DataType> dataTypeMap;

    static {
        dataTypeMap = Stream.of(DataType.values())
                .collect(Collectors.toMap(DataType::getValue, type -> type));
    }

    private final Integer value;

    DataType(Integer value) {
        this.value = value;
    }

    public static DataType from(Integer value) {
        return dataTypeMap.getOrDefault(value, DataType.UNKNOWN);
    }
}
