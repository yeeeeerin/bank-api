package com.depromeet.bank.service.impl;

import com.depromeet.bank.adaptor.openapi.AirGrade;
import com.depromeet.bank.adaptor.openapi.AirPollutionResponse;
import com.depromeet.bank.adaptor.openapi.OpenApiAdaptor;
import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import com.depromeet.bank.domain.AirInfo;
import com.depromeet.bank.exception.AirPollutionResponseNotFound;
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

//    @Override
//    @Transactional
//    public AirInfo createAirInfoByStationName(OpenApiStationName stationName) {
//        AirInfo airInfo = new AirInfo(openApiAdaptor
//                .getAirPollutionResponseByStationName(stationName)
//                .orElseThrow(() -> new AirPollutionResponseNotFound("AirPollutionReponse객체를 만들 수 없습니다.")), stationName);
//        log.info("airinfo : {}", airInfo.toString());
//        return airInfoRepository.save(airInfo);
//    }

    @Override
    @Transactional
    public AirInfo createAirInfoByStationName(OpenApiStationName stationName) {
        AirPollutionResponse response = openApiAdaptor.getAirPollutionResponseByStationName(stationName).orElseThrow(() -> new AirPollutionResponseNotFound("AirPollutionReponse객체를 만들수 없습니다."));
        AirInfo airInfo = new AirInfo(response, stationName, checkAirGrade(response));
        log.info("airinfo : {}", airInfo.toString());
        return airInfoRepository.save(airInfo);
    }

    @Override
    public AirGrade checkAirGrade(AirPollutionResponse response) {
        AirGrade pm10Grade = checkGradeByPm10Value(response.getPm10Value());
        AirGrade pm25Grade = checkGradeByPm25Value(response.getPm25Value());
        if (pm10Grade.getGrade() > pm25Grade.getGrade())
            return pm10Grade;
        return pm25Grade;
    }

    @Override
    public AirGrade checkGradeByPm10Value(Long pm10Value) {
        if (pm10Value >= 0 && pm10Value <= 15)
            return AirGrade.FIRST;
        else if (pm10Value >= 16 && pm10Value <= 30)
            return AirGrade.SECOND;
        else if (pm10Value >= 31 && pm10Value <= 40)
            return AirGrade.THIRD;
        else if (pm10Value >= 41 && pm10Value <= 50)
            return AirGrade.FOUTH;
        else if (pm10Value >= 51 && pm10Value <= 75)
            return AirGrade.FIFTH;
        else if (pm10Value >= 76 && pm10Value <= 100)
            return AirGrade.SIXTH;
        else if (pm10Value >= 101 && pm10Value <= 150)
            return AirGrade.SEVENTH;
        return AirGrade.EIGHTH;
    }

    @Override
    public AirGrade checkGradeByPm25Value(Long pm25Value) {
        if (pm25Value >= 0 && pm25Value <= 8)
            return AirGrade.FIRST;
        else if (pm25Value >= 9 && pm25Value <= 15)
            return AirGrade.SECOND;
        else if (pm25Value >= 16 && pm25Value <= 20)
            return AirGrade.THIRD;
        else if (pm25Value >= 21 && pm25Value <= 25)
            return AirGrade.FOUTH;
        else if (pm25Value >= 26 && pm25Value <= 37)
            return AirGrade.FIFTH;
        else if (pm25Value >= 38 && pm25Value <= 50)
            return AirGrade.SIXTH;
        else if (pm25Value >= 51 && pm25Value <= 75)
            return AirGrade.SEVENTH;
        return AirGrade.EIGHTH;
    }
}
