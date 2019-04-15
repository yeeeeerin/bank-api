package com.depromeet.bank.domain.rule;

import com.depromeet.bank.domain.data.attendance.DepromeetSessionType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Period {

    @Column
    private LocalDateTime fromAt;

    @Column
    private LocalDateTime toAt;

    private Period(LocalDateTime fromAt,
                   LocalDateTime toAt) {
        this.fromAt = Objects.requireNonNull(fromAt);
        this.toAt = Objects.requireNonNull(toAt);
    }

    public static Period of(LocalDateTime fromAt, LocalDateTime toAt) {
        return new Period(fromAt, toAt);
    }

    public static Period from(DepromeetSessionType sessionType) {
        LocalDateTime startedAt = sessionType.getStartedAt();
        return new Period(startedAt, startedAt);
    }

    public boolean contains(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }
        return (localDateTime.isAfter(fromAt) || localDateTime.isEqual(fromAt))
                && (localDateTime.isBefore(toAt) || localDateTime.isEqual(toAt));
    }
}
