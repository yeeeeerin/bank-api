package com.depromeet.bank.dto;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum InstrumentExpirationType {
    TRUE("true"),
    FALSE("false"),
    ALL("all");

    private final String value;

    InstrumentExpirationType(String value) {
        this.value = value;
    }

    public static InstrumentExpirationType from(String value) {
        return Stream.of(values())
                .filter(type -> type.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(ALL);
    }
}
