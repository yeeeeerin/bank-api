package com.depromeet.bank.adaptor.openapi;

import com.depromeet.bank.exception.AirPollutionNotFoundException;
import com.depromeet.bank.exception.OpenApiFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Optional;

@Slf4j
@Component
@PropertySource("classpath:open-api.properties")
public class OpenApiAdaptorImpl implements OpenApiAdaptor {
    private final RestTemplate restTemplate;
    private final ResponseParser responseParser;
    private final String serviceKey;

    public OpenApiAdaptorImpl(RestTemplate restTemplate, ResponseParser responseParser, @Value("${open-api.serviceKey}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.responseParser = responseParser;
        this.serviceKey = serviceKey;
    }

    @Override
    public Optional<AirPollution> getAirPollutionByStationName(String stationName) {
        try {
            String response = restTemplate.getForObject(makeURIfromStationName(stationName), String.class);
            return Optional.of(responseParser
                    .jsonToAirPollution(responseParser.xmlToJson(response)))
                    .orElseThrow(() -> new AirPollutionNotFoundException("AirPollution을 만들 수 없습니다."));
        } catch (OpenApiFailedException ex) {
            log.error("Failed to get open-api request", ex);
            return Optional.empty();
        } catch (IOException ex) {
            log.error("Failed to make json into AirPollution", ex);
            return Optional.empty();
        }
    }

    public URI makeURIfromStationName(String stationName) {
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
            log.error("{}", ex);
            throw new RuntimeException("Encoding에 실패했습니다.");
        }
    }
}
