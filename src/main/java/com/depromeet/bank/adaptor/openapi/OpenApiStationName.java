package com.depromeet.bank.adaptor.openapi;

public enum OpenApiStationName {
    SEOUL("종로구"), INCHEON("부평"), SUWON("광교동"), BUSAN("좌동"), DAEGOO("수성동");

    private String stationName;

    OpenApiStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getValue() {
        return stationName;
    }
}
