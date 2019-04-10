package com.depromeet.bank.repository;

import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.instrument.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    List<Instrument> findByExpiredAtLessThanAndSettlementStatus(LocalDateTime expiredAt, SettlementStatus settlementStatus);
}
