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
public class AirPollutionResponse {
    @JsonProperty("id")
    private Long airInfoId;
    private String stationName;
    private Integer pm10Value;
    private Integer pm25Value;
    private LocalDateTime dataTime;
    private AirGrade airGrade;

    private AirPollutionResponse(Long airInfoId,
                                 String stationName,
                                 Integer pm10Value,
                                 Integer pm25Value,
                                 LocalDateTime dataTime,
                                 AirGrade airGrade) {
        this.airInfoId = airInfoId;
        this.stationName = stationName;
        this.pm10Value = pm10Value;
        this.pm25Value = pm25Value;
        this.dataTime = dataTime;
        this.airGrade = airGrade;
    }

    public static AirPollutionResponse from(AirInfo airInfo) {
        Assert.notNull(airInfo, "'airInfo' must not be null");

        return new AirPollutionResponse(
                airInfo.getId(),
                airInfo.getStationName().getValue(),
                airInfo.getPm10Value(),
                airInfo.getPm25Value(),
                airInfo.getDataTime(),
                airInfo.getAirGrade()
        );
    }
}
