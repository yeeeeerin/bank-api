package com.depromeet.bank.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

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

    private LocalDateTime dataTime;

    public AirInfo(AirPollution airPollution, String stationName) {
        this.pm10Value = airPollution.getPm10Value();
        this.pm25Value = airPollution.getPm25Value();
        this.o3Value = airPollution.getO3Value();
        this.dataTime = airPollution.getDataTime();
        this.stationName = stationName;
    }
}
