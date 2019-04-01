package com.depromeet.bank.vo;

import com.depromeet.bank.dto.InstrumentRequest;
import lombok.Value;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;

@Value
public class InstrumentValue {
    private final String name;
    private final String description;
    private final ZonedDateTime expiredAt;

    private InstrumentValue(String name,
                            String description,
                            ZonedDateTime expiredAt) {
        this.name = name;
        this.description = description;
        this.expiredAt = expiredAt;
    }

    public static InstrumentValue from(InstrumentRequest instrumentRequest) {
        Assert.notNull(instrumentRequest, "'instrumentRequest' must not be null");
        String name = instrumentRequest.getName();
        String description = instrumentRequest.getDescription();
        ZonedDateTime zonedDateTime = instrumentRequest.getExpiredAt();
        return new InstrumentValue(name, description, zonedDateTime);
    }
}
