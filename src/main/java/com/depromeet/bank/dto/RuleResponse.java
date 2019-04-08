package com.depromeet.bank.dto;

import com.depromeet.bank.domain.rule.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleResponse {
    private Long id;
    private DataType dataType;
    private ComparisonType comparisonType;
    private NotType notType;
    private Long goal;
    private Long criterion;
    private LocalDateTime fromAt;
    private LocalDateTime toAt;
    private Double rate;

    public static RuleResponse from(AdjustmentRule adjustmentRule) {
        if (adjustmentRule == null) {
            return null;
        }
        Condition condition = adjustmentRule.getCondition();
        if (condition == null) {
            return null;
        }
        Reward reward = adjustmentRule.getReward();
        if (reward == null) {
            return null;
        }
        Long id = adjustmentRule.getAdjustmentRuleId();
        DataType dataType = condition.getDataType();
        ComparisonType comparisonType = condition.getComparisonType();
        NotType notType = condition.getNotType();
        Long goal = condition.getGoal();
        Long criterion = condition.getCriterion();
        LocalDateTime fromAt = condition.getPeriod().getFromAt();
        LocalDateTime toAt = condition.getPeriod().getToAt();
        Double rate = reward.getRate();
        return new RuleResponse(id, dataType, comparisonType, notType, goal, criterion, fromAt, toAt, rate);
    }
}
