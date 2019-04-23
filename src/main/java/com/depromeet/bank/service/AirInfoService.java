package com.depromeet.bank.service;

import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.domain.data.airinfo.AirInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirInfoService {
    List<AirInfo> createAirInfoByStationName(OpenApiStationName stationName);

    AirInfo synchronize(Long airInfoId);

    List<AirInfo> getAirInfos(Pageable pageable);
}
