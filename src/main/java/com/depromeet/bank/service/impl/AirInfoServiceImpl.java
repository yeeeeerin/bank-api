package com.depromeet.bank.service.impl;

import com.depromeet.bank.adapter.openapi.AirPollutionResponse;
import com.depromeet.bank.adapter.openapi.OpenApiAdapter;
import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.domain.data.airinfo.AirInfo;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.AirInfoRepository;
import com.depromeet.bank.service.AirInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirInfoServiceImpl implements AirInfoService {

    private final OpenApiAdapter openApiAdapter;
    private final AirInfoRepository airInfoRepository;

    @Override
    @Transactional
    public List<AirInfo> createAirInfoByStationName(OpenApiStationName stationName) {
        AirPollutionResponse response = openApiAdapter.getAirPollution(stationName);
        // 중복 생성 방지
        return response.getAirPollutions().stream()
                .map(airPollution -> {
                    LocalDateTime dataTime = airPollution.getDataTime();
                    AirInfo airInfo = airInfoRepository.findByStationNameAndDataTime(stationName.getValue(), dataTime)
                            .orElseGet(() -> new AirInfo(airPollution, stationName));
                    log.info("airinfo : {}", airInfo.toString());
                    return airInfoRepository.save(airInfo);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AirInfo synchronize(Long airInfoId) {
        AirInfo airInfo = airInfoRepository.findById(airInfoId).orElseThrow(NotFoundException::new);
        OpenApiStationName stationName = airInfo.getStationName();
        LocalDateTime dataTime = airInfo.getDataTime();
        if (dataTime == null) {
            return airInfo;
        }
        AirPollutionResponse response = openApiAdapter.getAirPollution(stationName);
        return response.getAirPollutions().stream()
                .filter(airPollution -> dataTime.isEqual(airPollution.getDataTime()))
                .findFirst()
                .map(airPollution -> airInfoRepository.save(airInfo.update(airPollution)))
                .orElse(airInfo);
    }
}
