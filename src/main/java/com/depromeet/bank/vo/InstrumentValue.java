package com.depromeet.bank.vo;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class InstrumentValue {
    private final String name;
    private final String description;
    private final LocalDateTime expiredAt;
}
