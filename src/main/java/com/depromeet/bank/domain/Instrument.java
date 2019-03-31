package com.depromeet.bank.domain;

import com.depromeet.bank.vo.InstrumentValue;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
public class Instrument {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private ZonedDateTime expiredAt;

    protected Instrument() {
    }

    private Instrument(String name, String description, ZonedDateTime expiredAt) {
        id = null;
        this.name = name;
        this.description = description;
        this.expiredAt = expiredAt;
    }

    public static Instrument from(InstrumentValue instrumentValue) {
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        String name = instrumentValue.getName();
        String description = instrumentValue.getDescription();
        ZonedDateTime expiredAt = instrumentValue.getExpiredAt();
        return new Instrument(name, description, expiredAt);
    }

    //todo 뭔가 전 방법이 더 괜찮은거같아여ㅠㅠ
    public Instrument update(InstrumentValue instrumentValue) {
        if (instrumentValue == null) {
            return this;
        }
        String requestedName = instrumentValue.getName();
        if (requestedName != null) {
            name = requestedName;
        }
        String requestedDescription = instrumentValue.getDescription();
        if (requestedDescription != null) {
            description = requestedDescription;
        }
        ZonedDateTime requestedExpiredAt = instrumentValue.getExpiredAt();
        if (requestedExpiredAt != null) {
            expiredAt = requestedExpiredAt;
        }
        return this;
    }
}
