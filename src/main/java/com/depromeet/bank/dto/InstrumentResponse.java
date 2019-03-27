package com.depromeet.bank.dto;

import com.depromeet.bank.domain.Instrument;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;

@Getter
@ToString
public class InstrumentResponse {
    private Long id;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private ZonedDateTime expiredAt;

    private InstrumentResponse(Long id, String name, String description, ZonedDateTime expiredAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.expiredAt = expiredAt;
    }

    public static InstrumentResponse from(Instrument instrument) {
        Assert.notNull(instrument, "'instrument' must not be null");
        Long id = instrument.getId();
        String name = instrument.getName();
        String description = instrument.getDescription();
        ZonedDateTime expiredAt = instrument.getExpiredAt();
        return new InstrumentResponse(id, name, description, expiredAt);
    }
}