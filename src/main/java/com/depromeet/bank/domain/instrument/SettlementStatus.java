package com.depromeet.bank.domain.instrument;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SettlementStatus {
    INCOMPLETE(1),
    COMPLETE(2),
    UNKNOWN(null);

    private static final Map<Integer, SettlementStatus> statusMap;

    static {
        statusMap = Stream.of(SettlementStatus.values())
                .collect(Collectors.toMap(SettlementStatus::getValue, type -> type));
    }

    private Integer value;

    SettlementStatus(Integer value) {
        this.value = value;
    }

    public static SettlementStatus from(Integer value) {
        return statusMap.getOrDefault(value, SettlementStatus.UNKNOWN);
    }

    public Integer getValue() {
        return value;
    }
}
