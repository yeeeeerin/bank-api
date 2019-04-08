package com.depromeet.bank.repository;

import com.depromeet.bank.domain.instrument.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

}
