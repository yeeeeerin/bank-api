package com.depromeet.bank.adapter.openapi;

public interface OpenApiAdapter {
    AirPollutionResponse getAirPollutionResponseByStationName(OpenApiStationName stationName);
}
