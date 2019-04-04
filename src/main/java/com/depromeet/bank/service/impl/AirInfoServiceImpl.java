package com.depromeet.bank.service.impl;

import com.depromeet.bank.adaptor.openapi.OpenApiAdaptor;
import com.depromeet.bank.domain.AirInfo;
import com.depromeet.bank.exception.AirPollutionNotFoundException;
import com.depromeet.bank.repository.AirInfoRepository;
import com.depromeet.bank.service.AirInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
public class AirInfoServiceImpl implements AirInfoService {

    private final OpenApiAdaptor openApiAdaptor;
    private final AirInfoRepository airInfoRepository;

    public AirInfoServiceImpl(OpenApiAdaptor openApiAdaptor, AirInfoRepository airInfoRepository) {
        this.openApiAdaptor = openApiAdaptor;
        this.airInfoRepository = airInfoRepository;
    }

    @Override
    @Transactional
    public AirInfo createAirInfoByStationName(String stationName) {
        AirInfo airInfo = new AirInfo(openApiAdaptor
                .getAirPollutionByStationName(stationName)
                .get(), stationName);
        log.info("airinfo : {}", airInfo.toString());
        return airInfoRepository.save(airInfo);
    }
}
