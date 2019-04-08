package com.depromeet.bank.service;

import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import com.depromeet.bank.domain.AirInfo;

import java.io.IOException;

public interface AirInfoService {
    public AirInfo createAirInfoByStationName(OpenApiStationName stationName);
}
