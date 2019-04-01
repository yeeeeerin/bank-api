package com.depromeet.bank.service.impl;

import com.depromeet.bank.config.RestTemplateConfig;
import com.depromeet.bank.domain.Air;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.XML;
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

    public String updateByStationName(String stationName) throws UnsupportedEncodingException {
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
        String response = restTemplateConfig.restTemplate().getForObject(url ,String.class);
        logger.info("{}", response);
        return response;
    }

    public JSONObject parseXMLToJson(String response) {
        JSONObject json = XML.toJSONObject(response);
        return json;
    }

    public Air jsonToAir(JSONObject response) {
        Gson gson = new Gson();
        String res = String.valueOf(response.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item"));
        return gson.fromJson(res, Air.class);
    }
}
