package com.depromeet.bank.adaptor.openapi;

public interface OpenApiAdaptor {
    AirPollutionResponse getAirPollutionResponseByStationName(OpenApiStationName stationName);
}
