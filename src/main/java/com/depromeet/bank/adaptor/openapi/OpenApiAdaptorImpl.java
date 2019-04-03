package com.depromeet.bank.adaptor.openapi;

import com.depromeet.bank.domain.AirPollution;
import com.depromeet.bank.utils.JsonUriXml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
public class OpenApiAdaptorImpl implements OpenApiAdaptor {
    private final JsonUriXml jsonUriXml;
    private final RestTemplate restTemplate;

    public OpenApiAdaptorImpl(JsonUriXml jsonUriXml, RestTemplate restTemplate) {
        this.jsonUriXml = jsonUriXml;
        this.restTemplate = restTemplate;
    }

//    @Override
//    public Optional<AirPollution> getAirPollutionByStationName(URI uri) throws IOException {
//            try {
//                String response = restTemplate.getForObject(uri, String.class);
//                return Optional.of(jsonUriXml.jsonToAirPollution(jsonUriXml.xmlToJson(response)));
//            } catch (RestClientException ex) {
//                log.error("Failed to get open-api request");
//                throw new RestClientException("유효하지 않은 요청입니다.");
//            } catch (IOException ex) {
//                throw new IOException("IO예외가 발생했습니다.");
//            }
//    }

    @Override
    public Optional<AirPollution> getAirPollutionByStationName(String stationName) throws IOException {
            try {
                String response = restTemplate.getForObject(jsonUriXml.makeURIfromStationName(stationName), String.class);
                return Optional.of(jsonUriXml.jsonToAirPollution(jsonUriXml.xmlToJson(response)));
            } catch (RestClientException ex) {
                log.error("Failed to get open-api request");
                throw new RestClientException("유효하지 않은 요청입니다.");
            } catch (IOException ex) {
                throw new IOException("IO예외가 발생했습니다.");
            }
    }


}
