package com.depromeet.bank.controller;

import com.depromeet.bank.service.impl.AirServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirControllerTest {
    public static final Logger logger =  LoggerFactory.getLogger(AirControllerTest.class);

    @Autowired
    private AirServiceImpl airService;

    @Test
    public void stationName으로미세먼지정보요청하기() throws UnsupportedEncodingException {
        ResponseEntity<String> response = airService.updateByStationName("종로구");
        logger.info("{}", response);
    }
}
