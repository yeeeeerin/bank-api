package com.depromeet.bank.adaptor.openapi;

import java.util.stream.Stream;

public enum OpenApiStationName {
    SEOUL("종로구"), INCHEON("부평"), SUWON("광교동"), BUSAN("좌동"), DAEGOO("수성동");

    private String stationName;

    OpenApiStationName(String stationName) {
        this.stationName = stationName;
    }

    public static OpenApiStationName from(String stationName) {
        return Stream.of(values())
                .filter(type -> type.stationName.equalsIgnoreCase(stationName))
                .findFirst()
                .orElse(null);
    }

    public String getValue() {
        return stationName;
    }
}
