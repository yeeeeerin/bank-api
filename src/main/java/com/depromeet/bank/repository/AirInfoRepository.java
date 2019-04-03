package com.depromeet.bank.repository;

import com.depromeet.bank.domain.AirInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirInfoRepository extends JpaRepository<AirInfo, Long> {
    Optional<AirInfo> findByStationName(String stationName);
}
