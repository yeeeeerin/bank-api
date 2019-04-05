package com.depromeet.bank.dto;

import com.depromeet.bank.domain.rule.*;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString
public class AdjustmentRuleRequest {
    @NotNull
    private DataType dataType;
    @NotNull
    private ComparisonType comparisonType;
    @NotNull
    private NotType notType;
    @NotNull
    private Long goal;
    @NotNull
    private Long criterion;
    @NotNull
    private LocalDateTime fromAt;
    @NotNull
    private LocalDateTime toAt;
    @NotNull
    private Double rate;

    public Condition toCondition() {
        Period period = Period.of(fromAt, toAt);
        return Condition.of(dataType, goal, criterion, period, comparisonType, notType);
    }

    public Reward toReward() {
        return Reward.from(rate);
    }
}
