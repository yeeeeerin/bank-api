package com.depromeet.bank.dto;

import com.depromeet.bank.domain.Instrument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstrumentResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime expiredAt;

    private InstrumentResponse(Long id, String name, String description, LocalDateTime expiredAt) {
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
        LocalDateTime expiredAt = instrument.getExpiredAt().toLocalDateTime().plusHours(9);
        return new InstrumentResponse(id, name, description, expiredAt);
    }
}
