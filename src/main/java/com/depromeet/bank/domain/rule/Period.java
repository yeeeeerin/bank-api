package com.depromeet.bank.domain.rule;

import com.depromeet.bank.converter.TimeUnitConverter;
import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Period {
    @Convert(converter = TimeUnitConverter.class)
    private TimeUnit timeUnit;

    private Integer cycleTime;

    private LocalDateTime fromAt;

    private LocalDateTime toAt;

    private Period(TimeUnit timeUnit,
                   Integer cycleTime,
                   LocalDateTime fromAt,
                   LocalDateTime toAt) {
        this.timeUnit = timeUnit;
        this.cycleTime = cycleTime;
        this.fromAt = fromAt;
        this.toAt = toAt;
    }

    public static Period from(DepromeetSessionType sessionType) {
        LocalDateTime startedAt = sessionType.getStartedAt();
        return new Period(TimeUnit.DAYS, 7, startedAt, startedAt);
    }
}
