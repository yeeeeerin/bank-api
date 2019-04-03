package com.depromeet.bank.utils;

import com.depromeet.bank.domain.AirPollution;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import java.net.URI;
import java.util.Optional;

@Slf4j
public class JsonUriXmlTest {

    @Autowired
    private JsonUriXml jsonUriXml;

    @Test
    public void uri만들기() throws Exception {
        URI uri = jsonUriXml.makeURIfromStationName("광교동");

    }

    @Test
    public void xml파싱() throws Exception {
        JSONObject object = jsonUriXml.xmlToJson(openApiAdaptor.getAirPollutionByStationName(uri).get());
        log.info("result : {}", object);
    }
}
