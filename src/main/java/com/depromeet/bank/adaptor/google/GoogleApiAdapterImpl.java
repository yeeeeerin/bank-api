package com.depromeet.bank.adaptor.google;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
@PropertySource("classpath:google-api.properties")
public class GoogleApiAdapterImpl implements GoogleApiAdapter {
    private final String apiKey;
    private final RestTemplate restTemplate;

    public GoogleApiAdapterImpl(RestTemplate restTemplate,
                                @Value("${google.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Override
    public Optional<GoogleApiResponse> findBySheetIdAndRange(String sheetId, Range range) {
        URI requestUri = UriComponentsBuilder.fromHttpUrl("https://sheets.googleapis.com/v4/spreadsheets/{sheetId}")
                .queryParam("key", apiKey)
                .queryParam("ranges", range.format())
                .queryParam("includeGridData", true)
                .buildAndExpand(sheetId)
                .toUri();
        try {
            GoogleApiResponse googleApiResponse = restTemplate.getForObject(requestUri, GoogleApiResponse.class);
            return Optional.ofNullable(googleApiResponse);
        } catch (RestClientException ex) {
            log.error("Failed to get sheet data from Google-api.", ex);
            return Optional.empty();
        }
    }
}
