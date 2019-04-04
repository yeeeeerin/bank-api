package com.depromeet.bank.adaptor.openapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirPollution {

    private Long pm10Value;

    private Long pm25Value;

    private Long o3Value;

    private LocalDateTime dataTime;

    public AirPollution setDataTime(String dataTime) {
        this.dataTime = LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return this;
    }
}
