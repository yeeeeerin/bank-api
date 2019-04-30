package com.depromeet.bank.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class GitAccountRequest {

    @NotNull
    private String url;
}
