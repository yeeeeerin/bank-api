package com.depromeet.bank.repository;

import com.depromeet.bank.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

}
