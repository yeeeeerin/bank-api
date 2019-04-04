package com.depromeet.bank.adaptor.openapi;

import java.io.IOException;
import java.util.Optional;

public interface OpenApiAdaptor {
    Optional<AirPollution> getAirPollutionByStationName(String stationName);

}
