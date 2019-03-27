package com.depromeet.bank.domain;

import com.depromeet.bank.vo.InstrumentValue;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Optional;

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
        this.id = null;
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

    public Instrument update(InstrumentValue instrumentValue) {
        Optional.ofNullable(instrumentValue)
                .map(InstrumentValue::getName)
                .ifPresent(name -> this.name = name);
        Optional.ofNullable(instrumentValue)
                .map(InstrumentValue::getDescription)
                .ifPresent(description -> this.description = description);
        Optional.ofNullable(instrumentValue)
                .map(InstrumentValue::getExpiredAt)
                .ifPresent(expiredAt -> this.expiredAt = expiredAt);
        return this;
    }
}
