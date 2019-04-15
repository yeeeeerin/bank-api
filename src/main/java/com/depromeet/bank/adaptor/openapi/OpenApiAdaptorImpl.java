package com.depromeet.bank.adaptor.openapi;

import com.depromeet.bank.exception.AirPollutionResponseNotFound;
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
    private final String serviceKey;

    public OpenApiAdaptorImpl(RestTemplate restTemplate, @Value("${open-api.serviceKey}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
    }

    @Override
    public Optional<AirPollutionResponse> getAirPollutionResponseByStationName(OpenApiStationName stationName) {
        try {
            AirPollutionResponse response = restTemplate.getForObject(makeURIfromStationName(stationName), AirPollutionResponse.class);
            return Optional.of(response);
        } catch (OpenApiFailedException ex) {
            log.error("Failed to get open-api request", ex);
            return Optional.empty();
        }
    }

    @Override
    public AirGrade checkAirGrade(AirPollutionResponse response) {
        AirGrade pm10Grade = checkGradeByPm10Value(response.getPm10Value());
        AirGrade pm25Grade = checkGradeByPm25Value(response.getPm25Value());
        if (pm10Grade.getGrade() > pm25Grade.getGrade())
            return pm10Grade;
        return pm25Grade;
    }

    @Override
    public AirGrade checkGradeByPm10Value(Long pm10Value) {
        if (pm10Value >= 0 && pm10Value <= 15)
            return AirGrade.FIRST;
        else if (pm10Value >= 16 && pm10Value <= 30)
            return AirGrade.SECOND;
        else if (pm10Value >= 31 && pm10Value <= 40)
            return AirGrade.THIRD;
        else if (pm10Value >= 41 && pm10Value <= 50)
            return AirGrade.FOUTH;
        else if (pm10Value >= 51 && pm10Value <= 75)
            return AirGrade.FIFTH;
        else if (pm10Value >= 76 && pm10Value <= 100)
            return AirGrade.SIXTH;
        else if (pm10Value >= 101 && pm10Value <= 150)
            return AirGrade.SEVENTH;
        return AirGrade.EIGHTH;
    }

    @Override
    public AirGrade checkGradeByPm25Value(Long pm25Value) {
        if (pm25Value >= 0 && pm25Value <= 8)
            return AirGrade.FIRST;
        else if (pm25Value >= 9 && pm25Value <= 15)
            return AirGrade.SECOND;
        else if (pm25Value >= 16 && pm25Value <= 20)
            return AirGrade.THIRD;
        else if (pm25Value >= 21 && pm25Value <= 25)
            return AirGrade.FOUTH;
        else if (pm25Value >= 26 && pm25Value <= 37)
            return AirGrade.FIFTH;
        else if (pm25Value >= 38 && pm25Value <= 50)
            return AirGrade.SIXTH;
        else if (pm25Value >= 51 && pm25Value <= 75)
            return AirGrade.SEVENTH;
        return AirGrade.EIGHTH;
    }


    public URI makeURIfromStationName(OpenApiStationName stationName) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("openapi.airkorea.or.kr")
                    .path("/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
                    .queryParam("stationName", URLEncoder.encode(stationName.getValue(), "UTF-8"))
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
