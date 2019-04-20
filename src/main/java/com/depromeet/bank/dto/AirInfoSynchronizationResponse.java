package com.depromeet.bank.dto;

import com.depromeet.bank.adapter.openapi.AirGrade;
import com.depromeet.bank.domain.data.airinfo.AirInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class AirInfoSynchronizationResponse {
    @JsonProperty("id")
    private Long airInfoId;
    private String stationName;
    private Long pm10Value;
    private Long pm25Value;
    private Integer pm10Grade;
    private Integer pm25Grade;
    private LocalDateTime dataTime;
    private AirGrade airGrade;

    private AirInfoSynchronizationResponse(Long airInfoId,
                                           String stationName,
                                           Long pm10Value,
                                           Long pm25Value,
                                           Integer pm10Grade,
                                           Integer pm25Grade,
                                           LocalDateTime dataTime,
                                           AirGrade airGrade) {
        this.airInfoId = airInfoId;
        this.stationName = stationName;
        this.pm10Value = pm10Value;
        this.pm25Value = pm25Value;
        this.pm10Grade = pm10Grade;
        this.pm25Grade = pm25Grade;
        this.dataTime = dataTime;
        this.airGrade = airGrade;
    }

    public static AirInfoSynchronizationResponse from(AirInfo airInfo) {
        Assert.notNull(airInfo, "'airInfo' must not be null");

        return new AirInfoSynchronizationResponse(
                airInfo.getId(),
                airInfo.getStationName(),
                airInfo.getPm10Value(),
                airInfo.getPm25Value(),
                airInfo.getPm10Grade(),
                airInfo.getPm25Grade(),
                airInfo.getDataTime(),
                airInfo.getAirGrade()
        );
    }
}
