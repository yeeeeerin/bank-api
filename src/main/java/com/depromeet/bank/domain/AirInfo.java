package com.depromeet.bank.domain;

import com.depromeet.bank.adaptor.openapi.AirGrade;
import com.depromeet.bank.adaptor.openapi.AirPollutionResponse;
import com.depromeet.bank.adaptor.openapi.OpenApiStationName;
import com.depromeet.bank.converter.AirGradeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @Convert(converter = AirGradeConverter.class)
    private AirGrade airGrade;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public AirInfo(AirPollutionResponse.Body.Item item, OpenApiStationName stationName) {
        this.pm10Value = item.getPm10Value();
        this.pm25Value = item.getPm25Value();
        this.o3Value = item.getO3Value();
        this.pm10Grade = item.getPm10Grade();
        this.pm25Grade = item.getPm25Grade();
        this.dataTime = setDataTime(item.getDataTime());
        this.stationName = stationName.getValue();
        this.airGrade = AirGrade.from(item);
    }

    private LocalDateTime setDataTime(String dataTime) {
        return LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public AirInfo update(AirPollutionResponse.Body.Item item) {
        this.pm10Value = item.getPm10Value();
        this.pm25Value = item.getPm25Value();
        this.o3Value = item.getO3Value();
        this.pm10Grade = item.getPm10Grade();
        this.pm25Grade = item.getPm25Grade();
        this.dataTime = setDataTime(item.getDataTime());
        this.airGrade = AirGrade.from(item);
        return this;
    }

}
