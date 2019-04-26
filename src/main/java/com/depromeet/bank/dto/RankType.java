package com.depromeet.bank.dto;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RankType {
    ACCOUNT, UNKNOWN;

    public static final String VALID_TYPES = Arrays.stream(RankType.values())
            .filter(type -> type != UNKNOWN)
            .map(RankType::name)
            .collect(Collectors.joining(","));

    public static RankType from(String value) {
        return Stream.of(values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
