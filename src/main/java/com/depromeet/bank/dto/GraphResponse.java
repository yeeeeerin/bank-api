package com.depromeet.bank.dto;

import com.depromeet.bank.vo.GraphValue;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class GraphResponse {
    private LocalDateTime time;
    private Long number;

    public static GraphResponse from(GraphValue graphValue) {
        GraphResponse graphResponse = new GraphResponse();
        graphResponse.time = graphValue.getTime();
        graphResponse.number = graphValue.getNumber();
        return graphResponse;
    }
}
