package com.depromeet.bank.adaptor.openapi;

import java.util.Optional;

public interface OpenApiAdaptor {
    Optional<String> getAirPollutionByStationName(String stationName);
}
