package com.depromeet.bank.domain.instrument;

import com.depromeet.bank.converter.SettlementStatusConverter;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.rule.AdjustmentRule;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instrument {
    @Id
    @GeneratedValue
    private Long instrumentId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private LocalDateTime expiredAt;

    @Column
    @Convert(converter = SettlementStatusConverter.class)
    private SettlementStatus settlementStatus;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany
    private List<Account> accounts;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AdjustmentRule> adjustmentRules;

    private Instrument(String name,
                       String description,
                       LocalDateTime expiredAt,
                       List<AdjustmentRule> adjustmentRules) {
        this.instrumentId = null;
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.expiredAt = Objects.requireNonNull(expiredAt);
        this.settlementStatus = Optional.ofNullable(settlementStatus).orElse(SettlementStatus.INCOMPLETE);
        this.adjustmentRules = new ArrayList<>(adjustmentRules);
    }

    public static Instrument from(InstrumentValue instrumentValue) {
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        String name = instrumentValue.getName();
        String description = instrumentValue.getDescription();
        LocalDateTime expiredAt = instrumentValue.getExpiredAt();
        return new Instrument(name, description, expiredAt, Collections.emptyList());
    }

    public Instrument update(InstrumentValue instrumentValue) {
        if (instrumentValue == null) {
            return this;
        }
        String requestedName = instrumentValue.getName();
        if (requestedName != null) {
            this.name = requestedName;
        }
        String requestedDescription = instrumentValue.getDescription();
        if (requestedDescription != null) {
            this.description = requestedDescription;
        }
        LocalDateTime requestedExpiredAt = instrumentValue.getExpiredAt();
        if (requestedExpiredAt != null) {
            this.expiredAt = requestedExpiredAt;
        }
        return this;
    }

    public Instrument setAsCompleted() {
        this.settlementStatus = SettlementStatus.COMPLETE;
        return this;
    }

    public Instrument addAdjustmentRule(AdjustmentRule adjustmentRule) {
        if (adjustmentRule == null) {
            return this;
        }
        if (adjustmentRules.contains(adjustmentRule)) {
            return this;
        }
        adjustmentRules.add(adjustmentRule);
        return this;
    }

    public Instrument removeAdjustmentRule(Long adjustmentRuleId) {
        if (adjustmentRuleId == null) {
            return this;
        }
        this.adjustmentRules = adjustmentRules.stream()
                .filter(rule -> !rule.getAdjustmentRuleId().equals(adjustmentRuleId))
                .collect(Collectors.toList());
        return this;
    }
}
