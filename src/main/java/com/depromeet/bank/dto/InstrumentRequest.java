package com.depromeet.bank.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class InstrumentRequest {
    private String name;
    private String description;
    private LocalDateTime expiredAt;
}
