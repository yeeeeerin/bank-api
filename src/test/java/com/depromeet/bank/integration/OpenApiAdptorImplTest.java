package com.depromeet.bank.integration;

import com.depromeet.bank.adaptor.openapi.OpenApiAdaptorImpl;
import com.depromeet.bank.domain.AirPollution;
import com.depromeet.bank.utils.JsonUriXml;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class OpenApiAdptorImplTest {
    private URI uri;

    @Autowired
    private OpenApiAdaptorImpl openApiAdaptor;

    @Autowired
    private JsonUriXml jsonUriXml;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        uri = jsonUriXml.makeURIfromStationName("광교동");
    }


    @Test
    public void jsonToAirPollution() throws IOException {
        JSONObject object = jsonUriXml.xmlToJson(openApiAdaptor.getAirPollutionByStationName("광교동").toString());
        AirPollution pollution = jsonUriXml.jsonToAirPollution(object);
        log.info("{}", pollution);
    }

}
