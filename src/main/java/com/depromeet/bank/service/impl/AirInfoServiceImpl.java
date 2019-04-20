package com.depromeet.bank.service.impl;

import com.depromeet.bank.adaptor.openapi.AirPollutionResponse;
import com.depromeet.bank.adaptor.openapi.OpenApiAdaptor;
import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import com.depromeet.bank.domain.data.airinfo.AirInfo;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.AirInfoRepository;
import com.depromeet.bank.service.AirInfoService;
import com.depromeet.bank.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirInfoServiceImpl implements AirInfoService {

    private final OpenApiAdaptor openApiAdaptor;
    private final AirInfoRepository airInfoRepository;

    @Override
    @Transactional
    public AirInfo createAirInfoByStationName(OpenApiStationName stationName) {
        AirPollutionResponse response = openApiAdaptor.getAirPollutionResponseByStationName(stationName);
        // 중복 생성 방지
        AirPollutionResponse.Body.Item item = response.getItem();
        LocalDateTime dataTime = DateTimeUtils.parseFromDataTime(item.getDataTime());
        AirInfo airInfo = airInfoRepository.findByStationNameAndDataTime(stationName.getValue(), dataTime)
                .orElseGet(() -> new AirInfo(item, stationName));
        log.info("airinfo : {}", airInfo.toString());
        return airInfoRepository.save(airInfo);
    }

    @Override
    @Transactional
    public AirInfo synchronize(Long airInfoId) {
        AirInfo airInfo = airInfoRepository.findById(airInfoId).orElseThrow(NotFoundException::new);
        OpenApiStationName stationName = OpenApiStationName.from(airInfo.getStationName());
        LocalDateTime dataTime = airInfo.getDataTime();
        if (dataTime == null) {
            return airInfo;
        }
        AirPollutionResponse response = openApiAdaptor.getAirPollutionResponseByStationName(stationName);
        AirPollutionResponse.Body.Item itemResult = response.getBody().getItems().stream()
                .filter(item -> dataTime.isEqual(DateTimeUtils.parseFromDataTime(item.getDataTime())))
                .findFirst()
                .orElse(null);
        if (itemResult == null) {
            return airInfo;
        }
        return airInfoRepository.save(airInfo.update(itemResult));
    }
}
