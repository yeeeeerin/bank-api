package com.depromeet.bank.domain.rule;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AdjustmentRule {
    @Id
    @GeneratedValue
    @Column(name = "adjustment_rule_id")
    private Long adjustmentRuleId;

    @Embedded
    private Condition condition;

    @Embedded
    private Reward reward;

    @Column
    private boolean satisfied;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private AdjustmentRule(Long adjustmentRuleId, Condition condition, Reward reward) {
        this.adjustmentRuleId = adjustmentRuleId;
        this.condition = Objects.requireNonNull(condition);
        this.reward = Objects.requireNonNull(reward);
    }

    public static AdjustmentRule of(Condition condition, Reward reward) {
        return new AdjustmentRule(null, condition, reward);
    }
}
