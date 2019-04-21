package com.depromeet.bank.repository;

import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.instrument.SettlementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    List<Instrument> findByToBeSettledAtLessThanAndSettlementStatus(LocalDateTime toBeSettledAt, SettlementStatus settlementStatus);

    Page<Instrument> findByExpiredAtGreaterThan(LocalDateTime localDateTime, Pageable pageable);

    Page<Instrument> findByExpiredAtLessThan(LocalDateTime localDateTime, Pageable pageable);
}
