package com.depromeet.bank.adaptor.openapi;

import com.depromeet.bank.domain.AirPollution;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public interface OpenApiAdaptor {
//    Optional<AirPollution> getAirPollutionByStationName(URI requestURI) throws IOException;
    Optional<AirPollution> getAirPollutionByStationName(String stationName) throws IOException;

}
