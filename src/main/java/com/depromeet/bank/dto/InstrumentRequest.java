package com.depromeet.bank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class InstrumentRequest {
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private ZonedDateTime expiredAt;
}
