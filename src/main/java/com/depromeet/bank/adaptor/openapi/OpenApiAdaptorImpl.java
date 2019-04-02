package com.depromeet.bank.adaptor.openapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Optional;

@Slf4j
@Component
@PropertySource("classpath:open-api.properties")
public class OpenApiAdaptorImpl implements OpenApiAdaptor {
    private final RestTemplate restTemplate;
    private final String serviceKey;

    public OpenApiAdaptorImpl(RestTemplate restTemplate, @Value("${open-api.serviceKey}") String serviceKey) {
        this.serviceKey = serviceKey;
        this.restTemplate = restTemplate;
    }

//    @Override
//    public Optional<String> getAirPollutionByStationName(String stationName) {
//        URI url = UriComponentsBuilder.newInstance()
//                .scheme("http")
//                .host("openapi.airkorea.or.kr")
//                .path("/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
//                .queryParam("stationName", URLEncoder.encode(stationName, "UTF-8"))
//                .queryParam("dataTerm", "daily")
//                .queryParam("pageNo", 1)
//                .queryParam("numOfRows", 1)
//                .queryParam("serviceKey", serviceKey)
//                .queryParam("ver", 1.3)
//                .build(true)
//                .toUri();
//        try {
//            String response = restTemplate.getForObject(url, String.class);
//            return Optional.of(response);
//        } catch (RestClientException ex) {
//           log.error("Failed to get open-api request");
//           return Optional.empty();
//        }
//    }
    @Override
    public Optional<String> getAirPollutionByStationName(String stationName) {
        try {
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
            try {
                String response = restTemplate.getForObject(url, String.class);
                return Optional.of(response);
            } catch (RestClientException ex) {
                log.error("Failed to get open-api request");
                return Optional.empty();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
