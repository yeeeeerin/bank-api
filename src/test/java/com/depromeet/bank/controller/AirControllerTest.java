package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Air;
import com.depromeet.bank.service.impl.AirServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AirControllerTest {
    private String response;
    private Air air;

    @Autowired
    private AirServiceImpl airService;

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
        Air air = gson.fromJson(String.valueOf(res), Air.class);
        log.info("air객체 : {}", air.toString());
    }

    @Test
    public void jsonToAir객체테스트() throws Exception {
        air = airService.jsonToAir(airService.parseXMLToJson(response));
        log.info("{}", air);
    }
}
