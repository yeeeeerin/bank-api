package com.depromeet.bank.adapter.google;

import com.depromeet.bank.exception.GoogleApiFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<String> parseAttendanceSigns(GoogleApiResponse googleApiResponse) {
        return Optional.ofNullable(googleApiResponse)
                .map(GoogleApiResponse::getSheets)
                .map(sheets -> sheets.get(0))
                .map(GoogleApiResponse.Sheet::getData)
                .map(dataList -> dataList.get(0))
                .map(GoogleApiResponse.SheetData::getRowData)
                .map(rowValuesList -> rowValuesList.stream()
                        .map(GoogleApiResponse.RowValues::getValues)
                        .map(rowValueList -> rowValueList.get(0))
                        .map(GoogleApiResponse.RowValue::getFormattedValue)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new GoogleApiFailedException("구글 API 응답을 파싱하는데 실패했습니다."));
    }
}
