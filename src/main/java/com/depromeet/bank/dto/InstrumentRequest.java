package com.depromeet.bank.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class InstrumentRequest {
    private String name;
    private String description;
    private ZonedDateTime expiredAt;
}
