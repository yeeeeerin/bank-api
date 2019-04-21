package com.depromeet.bank.adapter.openapi;

import com.depromeet.bank.exception.InternalServerErrorException;
import com.depromeet.bank.exception.OpenApiFailedException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
@PropertySource("classpath:open-api.properties")
public class OpenApiAdapterImpl implements OpenApiAdapter {
    private final RestTemplate restTemplate;
    private final String serviceKey;
    private final ObjectMapper objectMapper;

    public OpenApiAdapterImpl(RestTemplate restTemplate,
                              @Value("${open-api.serviceKey}") String serviceKey,
                              ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
        this.objectMapper = objectMapper;
    }

    @Override
    public AirPollutionResponse getAirPollution(OpenApiStationName stationName) {
        try {
            String result = restTemplate.getForObject(createURIOfAirPollution(stationName), String.class);
            return objectMapper.readValue(result, AirPollutionResponse.class);
        } catch (RestClientException ex) {
            log.error("Failed to get open-api request", ex);
            throw new OpenApiFailedException("미세먼지 api 요청에 실패했습니다");
        } catch (IOException ex) {
            log.error("Failed to read open-api request", ex);
            throw new InternalServerErrorException("미세먼지 api 요청을 읽는데 실패했습니다");
        }
    }

    private URI createURIOfAirPollution(OpenApiStationName stationName) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("openapi.airkorea.or.kr")
                .path("/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
                .queryParam("stationName", stationName.getValue())
                .queryParam("dataTerm", "daily")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1)
                .queryParam("serviceKey", serviceKey)
                .queryParam("ver", 1.3)
                .queryParam("_returnType", "json")
                .build()
                .toUri();
    }
}
