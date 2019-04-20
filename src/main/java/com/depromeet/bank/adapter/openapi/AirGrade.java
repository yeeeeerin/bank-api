package com.depromeet.bank.adapter.openapi;

import java.util.stream.Stream;

public enum AirGrade {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8);

    private Integer value;

    AirGrade(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static AirGrade from(Integer value) {
        return Stream.of(values())
                .filter(airGrade -> airGrade.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static AirGrade from(AirPollutionResponse.Body.Item item) {
        AirGrade pm10Grade = getPM10Grade(item.getPm10Value());
        AirGrade pm25Grade = getPM25Grade(item.getPm25Value());
        if (pm10Grade.getValue() >= pm25Grade.getValue())
            return pm10Grade;
        return pm25Grade;
    }

    private static AirGrade getPM10Grade(Long pm10Value) {
        if (pm10Value >= 0 && pm10Value <= 15)
            return AirGrade.FIRST;
        else if (pm10Value >= 16 && pm10Value <= 30)
            return AirGrade.SECOND;
        else if (pm10Value >= 31 && pm10Value <= 40)
            return AirGrade.THIRD;
        else if (pm10Value >= 41 && pm10Value <= 50)
            return AirGrade.FOURTH;
        else if (pm10Value >= 51 && pm10Value <= 75)
            return AirGrade.FIFTH;
        else if (pm10Value >= 76 && pm10Value <= 100)
            return AirGrade.SIXTH;
        else if (pm10Value >= 101 && pm10Value <= 150)
            return AirGrade.SEVENTH;
        return AirGrade.EIGHTH;
    }

    private static AirGrade getPM25Grade(Long pm25Value) {
        if (pm25Value >= 0 && pm25Value <= 8)
            return AirGrade.FIRST;
        else if (pm25Value >= 9 && pm25Value <= 15)
            return AirGrade.SECOND;
        else if (pm25Value >= 16 && pm25Value <= 20)
            return AirGrade.THIRD;
        else if (pm25Value >= 21 && pm25Value <= 25)
            return AirGrade.FOURTH;
        else if (pm25Value >= 26 && pm25Value <= 37)
            return AirGrade.FIFTH;
        else if (pm25Value >= 38 && pm25Value <= 50)
            return AirGrade.SIXTH;
        else if (pm25Value >= 51 && pm25Value <= 75)
            return AirGrade.SEVENTH;
        return AirGrade.EIGHTH;
    }
}
