package com.depromeet.bank.repository;

import com.depromeet.bank.domain.rule.AdjustmentRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjustmentRuleRepository extends JpaRepository<AdjustmentRule, Long> {
}
