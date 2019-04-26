package com.depromeet.bank.integration;

import com.depromeet.bank.adapter.openapi.AirPollutionResponse;
import com.depromeet.bank.adapter.openapi.OpenApiAdapter;
import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OpenApiAdapterTest {
    @Autowired
    private OpenApiAdapter apiAdaptor;

    @Test
    public void stationName을Enum으로했을때응답이되는지() {
        AirPollutionResponse response = apiAdaptor.getAirPollution(OpenApiStationName.SEOUL);
        log.info("{}", response);
        assertThat(response).isNotNull();
    }
}
