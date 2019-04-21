package com.depromeet.bank.adapter.openapi;

public interface OpenApiAdapter {
    AirPollutionResponse getAirPollution(OpenApiStationName stationName);
}
