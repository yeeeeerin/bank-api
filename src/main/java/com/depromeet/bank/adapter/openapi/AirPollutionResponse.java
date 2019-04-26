package com.depromeet.bank.adapter.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class AirPollutionResponse {
    @JsonProperty("list")
    private List<AirPollution> airPollutions;
}
