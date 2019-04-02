package com.depromeet.bank.repository;

import com.depromeet.bank.domain.AirPollution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirRepository extends JpaRepository<AirPollution, Long> {

}
