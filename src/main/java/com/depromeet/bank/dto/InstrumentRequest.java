package com.depromeet.bank.dto;

import com.depromeet.bank.vo.AdjustmentRuleValue;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class InstrumentRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private LocalDateTime expiredAt;
    @NotNull
    private LocalDateTime toBeSettledAt;
    @NotEmpty
    private List<AdjustmentRuleRequest> rules;

    public InstrumentValue toInstrumentValue() {
        return InstrumentValue.of(name, description, expiredAt, toBeSettledAt);
    }

    public List<AdjustmentRuleValue> toAdjustmentRuleValues() {
        return rules.stream()
                .map(adjustmentRuleRequest -> AdjustmentRuleValue.of(adjustmentRuleRequest.toCondition(), adjustmentRuleRequest.toReward()))
                .collect(Collectors.toList());
    }
}
