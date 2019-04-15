package com.depromeet.bank.integration;

import com.depromeet.bank.adaptor.openapi.AirGrade;
import com.depromeet.bank.adaptor.openapi.AirPollutionResponse;
import com.depromeet.bank.adaptor.openapi.OpenApiAdaptor;
import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OpenApiAdapterTest {
    @Autowired
    private OpenApiAdaptor apiAdaptor;

    @Test
    public void stationName을Enum으로했을때응답이되는지() {
        Optional<AirPollutionResponse> response =  apiAdaptor.getAirPollutionResponseByStationName(OpenApiStationName.SEOUL);
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    @Test
    public void 미세먼지등급테스트() {
        Optional<AirPollutionResponse> response =  apiAdaptor.getAirPollutionResponseByStationName(OpenApiStationName.INCHEON);
        log.info("response : {}" + response.toString());
        log.info("초미세먼지 지수 : " + response.get().getPm25Value());
        AirGrade pm10Value = apiAdaptor.checkGradeByPm10Value(response.get().getPm10Value());
        AirGrade pm25Value = apiAdaptor.checkGradeByPm10Value(response.get().getPm25Value());
        log.info("미세먼지 등급 : " + pm10Value.getGrade());
        log.info("미세먼지 등급 : " + pm10Value);

        log.info("초미세먼지 등급 : " + pm25Value.getGrade());
        log.info("초미세먼지 등급 : " + pm25Value);

        AirGrade finalGrade = apiAdaptor.checkAirGrade(response.get());
        log.info("최종등급 : " + finalGrade);
        log.info("최종등급 : " + finalGrade.getGrade());

    }
}
