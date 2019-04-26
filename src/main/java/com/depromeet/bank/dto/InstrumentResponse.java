package com.depromeet.bank.dto;

import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.rule.AdjustmentRule;
import com.depromeet.bank.domain.rule.Condition;
import com.depromeet.bank.domain.rule.DataType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstrumentResponse {
    private Long id;
    private String name;
    private String description;
    private DataType dataType;
    private LocalDateTime expiredAt;
    private LocalDateTime toBeSettledAt;
    private List<RuleResponse> rules;

    private InstrumentResponse(Long id,
                               String name,
                               String description,
                               DataType dataType,
                               LocalDateTime expiredAt,
                               LocalDateTime toBeSettledAt,
                               List<RuleResponse> rules) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dataType = dataType;
        this.expiredAt = expiredAt;
        this.toBeSettledAt = toBeSettledAt;
        this.rules = new ArrayList<>(rules);
    }

    public static InstrumentResponse from(Instrument instrument) {
        Assert.notNull(instrument, "'instrument' must not be null");
        Long id = instrument.getInstrumentId();
        String name = instrument.getName();
        String description = instrument.getDescription();
        DataType dataType = instrument.getAdjustmentRules()
                .stream()
                .findFirst()
                .map(AdjustmentRule::getCondition)
                .map(Condition::getDataType)
                .orElse(DataType.UNKNOWN);
        LocalDateTime expiredAt = instrument.getExpiredAt();
        LocalDateTime toBeSettledAt = instrument.getToBeSettledAt();
        List<RuleResponse> rules = instrument.getAdjustmentRules()
                .stream()
                .map(RuleResponse::from)
                .collect(Collectors.toList());
        return new InstrumentResponse(id, name, description, dataType, expiredAt, toBeSettledAt, rules);
    }
}
