package com.depromeet.bank.adapter.openapi;

import com.depromeet.bank.exception.OpenApiFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@PropertySource("classpath:open-api.properties")
public class OpenApiAdapterImpl implements OpenApiAdapter {
    private final RestTemplate restTemplate;
    private final String serviceKey;

    public OpenApiAdapterImpl(RestTemplate restTemplate, @Value("${open-api.serviceKey}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
    }

    @Override
    public AirPollutionResponse getAirPollutionResponseByStationName(OpenApiStationName stationName) {
        try {
            return restTemplate.getForObject(createURIOfAirPollution(stationName), AirPollutionResponse.class);
        } catch (RestClientException ex) {
            log.error("Failed to get open-api request", ex);
            throw new OpenApiFailedException("미세먼지 api 요청에 실패했습니다");
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
                .build()
                .toUri();
    }
}
