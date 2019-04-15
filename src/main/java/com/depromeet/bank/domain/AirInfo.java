package com.depromeet.bank.domain;

import com.depromeet.bank.adaptor.openapi.AirGrade;
import com.depromeet.bank.adaptor.openapi.AirPollutionResponse;
import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AirInfo {

    @Id
    @GeneratedValue
    private Long id;

    private String stationName;

    private Long pm10Value;

    private Long pm25Value;

    private Long o3Value;

    private Integer pm10Grade;

    private Integer pm25Grade;

    private LocalDateTime dataTime;

    private AirGrade airGrade;

    public AirInfo(AirPollutionResponse response, OpenApiStationName stationName, AirGrade airGrade) {
        this.pm10Value = response.getItem().getPm10Value();
        this.pm25Value = response.getItem().getPm25Value();
        this.o3Value = response.getItem().getO3Value();
        this.pm10Grade = response.getItem().getPm10Grade();
        this.pm25Grade = response.getItem().getPm25Grade();
        this.dataTime =  setDataTime(response.getItem().getDataTime());
        this.stationName = stationName.getValue();
        this.airGrade = airGrade;
    }

    public LocalDateTime setDataTime(String dataTime) {
        return LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
