package com.depromeet.bank.domain.rule;

import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Period {
    @Column
    @Enumerated(value = EnumType.STRING)
    private TimeUnit timeUnit;

    @Column
    private Integer cycleTime;

    @Column
    private LocalDateTime fromAt;

    @Column
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
