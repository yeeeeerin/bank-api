package com.depromeet.bank.service;

import com.depromeet.bank.domain.AirInfo;

import java.io.IOException;

public interface AirInfoService {
    public AirInfo createAirInfoByStationName(String stationName) throws IOException;
}
