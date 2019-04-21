package com.depromeet.bank.adapter.openapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class AirPollution {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataTime;
    private Integer pm10Value;
    private Integer pm25Value;
    private Double o3Value;
}
