package com.depromeet.bank.repository;

import com.depromeet.bank.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findByMemberId(Long memberId);
    List<Visitor> findByCreatedAtGreaterThanAndCreatedAtLessThan(LocalDateTime from, LocalDateTime to);
}
