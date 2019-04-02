package com.depromeet.bank.integration;

import com.depromeet.bank.domain.AirPollution;
import com.depromeet.bank.service.impl.AirPollutionServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AirPollutionControllerTest {
    private String response;
    private AirPollution airPollution;

    @Autowired
    private AirPollutionServiceImpl airService;

    @Before
    public void setUp() throws Exception {
        response = airService.updateByStationName("광교동");
    }

    @Test
    public void stationName으로미세먼지정보요청하기() throws UnsupportedEncodingException {
        log.info("{}", response);
    }

    @Test
    public void 응답내용JSON으로변환하기() throws Exception {
        JSONObject object = airService.parseXMLToJson(response);
        JSONObject res = object.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
        log.info("item : {}", res);
    }

    @Test
    public void json으로Air객체만들기() throws Exception {
        JSONObject object = airService.parseXMLToJson(response);
        JSONObject res = object.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
        log.info("item : {}", res);
        Gson gson = new Gson();
        AirPollution airPollution = gson.fromJson(String.valueOf(res), AirPollution.class);
        log.info("air객체 : {}", airPollution.toString());
    }

    @Test
    public void jsonToAir객체테스트() throws Exception {
        airPollution = airService.jsonToAir(airService.parseXMLToJson(response));
        log.info("{}", airPollution);
    }
}
