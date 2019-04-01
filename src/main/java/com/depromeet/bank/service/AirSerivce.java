package com.depromeet.bank.service;

import com.depromeet.bank.config.RestTemplateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AirSerivce {
    private static final Logger logger =  LoggerFactory.getLogger(AirSerivce.class);

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    private String servicekey = "wcHq6FOmjnHYiJ0N5t1DEiEjYfE9njsCuqWfxGkGTpNPlMNaAh3K%2FFQ9lHjrECOSGVrTvnaI5kakoMjjdmD3Ug%3D%3D";

    public ResponseEntity<String> updateByStationname(String stationName) {
        ResponseEntity<String> response = restTemplateConfig.restTemplate().getForEntity("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName={stationName}&dataTerm=daily&pageNo=1&numOfRows=1&{servicekey}&ver=1.3", String.class, stationName, servicekey);
        logger.info("{}", response);
        return response;
    }
}
