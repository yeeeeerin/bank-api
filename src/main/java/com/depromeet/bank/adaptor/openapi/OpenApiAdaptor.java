package com.depromeet.bank.adaptor.openapi;

import java.util.Optional;

public interface OpenApiAdaptor {
    Optional<AirPollutionResponse> getAirPollutionResponseByStationName(OpenApiStationName stationName);

    AirGrade checkAirGrade(AirPollutionResponse response);

    AirGrade checkGradeByPm10Value(Long pm10Value);

    AirGrade checkGradeByPm25Value(Long pm25Value);
}
