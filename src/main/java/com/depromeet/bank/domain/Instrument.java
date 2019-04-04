package com.depromeet.bank.domain;

import com.depromeet.bank.vo.InstrumentValue;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Instrument {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private LocalDateTime expiredAt;

    protected Instrument() {
    }

    private Instrument(String name, String description, LocalDateTime expiredAt) {
        this.id = null;
        this.name = name;
        this.description = description;
        this.expiredAt = expiredAt;
    }

    public static Instrument from(InstrumentValue instrumentValue) {
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        String name = instrumentValue.getName();
        String description = instrumentValue.getDescription();
        LocalDateTime expiredAt = instrumentValue.getExpiredAt();
        return new Instrument(name, description, expiredAt);
    }

    public Instrument update(InstrumentValue instrumentValue) {
        if (instrumentValue == null) {
            return this;
        }
        String requestedName = instrumentValue.getName();
        if (requestedName != null) {
            this.name = requestedName;
        }
        String requestedDescription = instrumentValue.getDescription();
        if (requestedDescription != null) {
            this.description = requestedDescription;
        }
        LocalDateTime requestedExpiredAt = instrumentValue.getExpiredAt();
        if (requestedExpiredAt != null) {
            this.expiredAt = requestedExpiredAt;
        }
        return this;
    }
}
