package com.depromeet.bank.integration;

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
}
