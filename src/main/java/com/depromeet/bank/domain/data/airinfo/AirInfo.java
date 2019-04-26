package com.depromeet.bank.domain.data.airinfo;

import com.depromeet.bank.adapter.openapi.AirGrade;
import com.depromeet.bank.adapter.openapi.AirPollution;
import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.converter.AirGradeConverter;
import com.depromeet.bank.converter.OpenApiStationNameConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AirInfo {

    @Id
    @GeneratedValue
    @Column(name = "air_info_id")
    private Long id;

    @Convert(converter = OpenApiStationNameConverter.class)
    private OpenApiStationName stationName;

    private Integer pm10Value;

    private Integer pm25Value;

    private Double o3Value;

    private LocalDateTime dataTime;

    @Convert(converter = AirGradeConverter.class)
    private AirGrade airGrade;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public AirInfo(AirPollution airPollution, OpenApiStationName stationName) {
        Assert.notNull(airPollution, "'airPollution' must not be null");
        Assert.notNull(stationName, "'stationName' must not be null");

        this.pm10Value = airPollution.getPm10Value();
        this.pm25Value = airPollution.getPm25Value();
        this.o3Value = airPollution.getO3Value();
        this.dataTime = airPollution.getDataTime();
        this.stationName = stationName;
        this.airGrade = AirGrade.from(airPollution);
    }

    public AirInfo update(AirPollution airPollution) {
        Assert.notNull(airPollution, "'airPollution' must not be null");

        this.pm10Value = airPollution.getPm10Value();
        this.pm25Value = airPollution.getPm25Value();
        this.o3Value = airPollution.getO3Value();
        this.dataTime = airPollution.getDataTime();
        this.airGrade = AirGrade.from(airPollution);
        return this;
    }
}
