package com.depromeet.bank.domain.rule;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdjustmentRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Condition condition;

    @Embedded
    private Reward reward;

    private AdjustmentRule(Long id, Condition condition, Reward reward) {
        this.id = id;
        this.condition = condition;
        this.reward = reward;
    }

    public static AdjustmentRule of(Condition condition, Reward reward) {
        return new AdjustmentRule(null, condition, reward);
    }
}
