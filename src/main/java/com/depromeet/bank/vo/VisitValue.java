package com.depromeet.bank.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class VisitValue {
    private final long point;
    private final long numberOfContinuousDays;
    private final long numberOfVisitor;
    private final boolean isBonus;
}
