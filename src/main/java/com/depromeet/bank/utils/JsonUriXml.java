package com.depromeet.bank.utils;

import com.depromeet.bank.domain.AirPollution;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Slf4j
@Component
@PropertySource("classpath:open-api.properties")
public class JsonUriXml {
    private final String serviceKey;

    public JsonUriXml(@Value("${open-api.serviceKey}") String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public URI makeURIfromStationName(String stationName) throws UnsupportedEncodingException {
        try {
            URI uri = UriComponentsBuilder.newInstance()
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
            return uri;
        } catch (UnsupportedEncodingException ex) {
            log.error("Encoding error occured : {}", ex);
            throw new UnsupportedEncodingException("인코딩 에러가 발생했습니다");
        }
    }

    public JSONObject xmlToJson(String response) throws IOException {
        return XML.toJSONObject(response);
    }

    public AirPollution jsonToAirPollution(JSONObject json) throws IOException {
        JSONObject object = json.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object.toString(), AirPollution.class);
    }
}
