package com.depromeet.bank.domain.rule;

import com.depromeet.bank.vo.AdjustmentRuleValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdjustmentRuleFactory {

    public AdjustmentRule createAdjustmentRule(AdjustmentRuleValue adjustmentRuleValue) {
        return AdjustmentRule.of(
                adjustmentRuleValue.getCondition(),
                adjustmentRuleValue.getReward()
        );
    }
}
