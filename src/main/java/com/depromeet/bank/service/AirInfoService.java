package com.depromeet.bank.service;

import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.domain.data.airinfo.AirInfo;

public interface AirInfoService {
    AirInfo createAirInfoByStationName(OpenApiStationName stationName);

    AirInfo synchronize(Long airInfoId);
}
