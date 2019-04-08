package com.depromeet.bank.vo;

import com.depromeet.bank.domain.rule.Condition;
import com.depromeet.bank.domain.rule.Reward;
import lombok.Value;

@Value(staticConstructor = "of")
public class AdjustmentRuleValue {
    private final Condition condition;
    private final Reward reward;
}
