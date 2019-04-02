package com.depromeet.bank.service.impl;

import com.depromeet.bank.adaptor.openapi.OpenApiAdaptorImpl;
import com.depromeet.bank.domain.AirPollution;
import com.depromeet.bank.service.AirPollutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirPollutionServiceImpl implements AirPollutionService {

    private final OpenApiAdaptorImpl openApiAdaptor;

    @Override
    public Optional<AirPollution> XmlToAirPollution(String response) {
        return Optional.empty();
    }
}
