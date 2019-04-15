package com.depromeet.bank.adaptor.openapi;

import java.util.Optional;

public interface OpenApiAdaptor {
    Optional<AirPollutionResponse> getAirPollutionResponseByStationName(OpenApiStationName stationName);
}
