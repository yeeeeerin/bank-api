package com.depromeet.bank.repository;

import com.depromeet.bank.domain.data.airinfo.AirInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AirInfoRepository extends JpaRepository<AirInfo, Long> {
    Optional<AirInfo> findByStationName(String stationName);

    Optional<AirInfo> findByStationNameAndDataTime(String stationName, LocalDateTime dataTime);
}
