package com.depromeet.bank.service.impl;

import com.depromeet.bank.config.RestTemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Service
public class AirServiceImpl {
    public static final Logger logger =  LoggerFactory.getLogger(AirServiceImpl.class);

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    private String serviceKey = "wcHq6FOmjnHYiJ0N5t1DEiEjYfE9njsCuqWfxGkGTpNPlMNaAh3K%2FFQ9lHjrECOSGVrTvnaI5kakoMjjdmD3Ug%3D%3D";

    public ResponseEntity<String> updateByStationName(String stationName) throws UnsupportedEncodingException {
        URI url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("openapi.airkorea.or.kr")
                .path("/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
                .queryParam("stationName", URLEncoder.encode(stationName, "UTF-8"))
                .queryParam("dataTerm", "daily")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1)
                .queryParam("serviceKey", serviceKey)
                .queryParam("ver", 1.3)
                .build(true)
                .toUri();
        logger.info("{}", url);
        ResponseEntity<String> response = restTemplateConfig.restTemplate().getForEntity(url ,String.class);
        return response;
    }
}
