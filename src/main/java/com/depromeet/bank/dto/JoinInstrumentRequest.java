package com.depromeet.bank.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class JoinInstrumentRequest {
    @NotNull
    private Long investment;
}
