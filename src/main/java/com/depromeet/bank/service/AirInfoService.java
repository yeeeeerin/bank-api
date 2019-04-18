package com.depromeet.bank.service;

import com.depromeet.bank.adaptor.openapi.AirGrade;
import com.depromeet.bank.adaptor.openapi.AirPollutionResponse;
import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import com.depromeet.bank.domain.AirInfo;

import java.io.IOException;

public interface AirInfoService {
    AirInfo createAirInfoByStationName(OpenApiStationName stationName);

    AirGrade checkAirGrade(AirPollutionResponse response);

    AirGrade checkGradeByPm10Value(Long pm10Value);

    AirGrade checkGradeByPm25Value(Long pm25Value);
}
