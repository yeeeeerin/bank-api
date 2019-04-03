package com.depromeet.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirPollution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long pm10Value;

    private Long pm25Value;

    private Long o3Value;

    private LocalDateTime dataTime;

    public AirPollution setDataTime(String dataTime) {
        this.dataTime = LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return this;
    }
}
