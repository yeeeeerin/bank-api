package com.depromeet.bank.vo;

import com.depromeet.bank.dto.InstrumentRequest;
import lombok.Value;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Value
public class InstrumentValue {
    private final String name;
    private final String description;
    private final LocalDateTime expiredAt;

    private InstrumentValue(String name,
                            String description,
                            LocalDateTime expiredAt) {
        this.name = name;
        this.description = description;
        this.expiredAt = expiredAt;
    }

    public static InstrumentValue from(InstrumentRequest instrumentRequest) {
        Assert.notNull(instrumentRequest, "'instrumentRequest' must not be null");
        String name = instrumentRequest.getName();
        String description = instrumentRequest.getDescription();
        LocalDateTime localDateTime = instrumentRequest.getExpiredAt();
        return new InstrumentValue(name, description, localDateTime);
    }
}
