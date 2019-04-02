package com.depromeet.bank.service;

import com.depromeet.bank.domain.AirPollution;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface AirPollutionService {
    Optional<AirPollution> XmlToAirPollution(String response);
}
