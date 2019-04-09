package com.depromeet.bank.domain.data.attendance;

import com.depromeet.bank.domain.rule.Period;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Getter
@ToString
public enum DepromeetSessionType {
    MARCH_SIXTEENTH(3, 16, "D"),
    MARCH_TWENTY_THIRD(3, 23, "E"),
    MARCH_THIRTY(3, 30, "F"),
    APRIL_SIXTH(4, 6, "G"),
    APRIL_THIRTEENTH(4, 13, "H"),
    APRIL_TWENTY_SEVENTH(4, 27, "I"),
    MAY_FOURTH(5, 4, "J"),
    MAY_ELEVENTH(5, 11, "K"),
    MAY_EIGHTEENTH(5, 18, "L"),
    MAY_TWENTY_FIFTH(5, 25, "M"),
    JUNE_FIRST(6, 1, "N"),
    JUNE_EIGHTH(6, 8, "O"),
    JUNE_TWENTY_SECOND(6, 22, "P"),
    JUNE_TWENTY_NINTH(6, 29, "Q"),
    JULY_SIXTH(6, 22, "R"),
    JULY_THIRTEENTH(7, 13, "S"),
    JULY_TWENTIETH(7, 20, "T"),
    JULY_TWENTY_SEVENTH(7, 27, "U"),
    AUGUST_THIRD(8, 3, "V"),
    UNKNOWN(1, 1, "");

    private int month;
    private int dayOfMonth;
    private String columnName;

    DepromeetSessionType(int month, int dayOfMonth, String columnName) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.columnName = columnName;
    }

    public LocalDateTime getStartedAt() {
        return LocalDateTime.of(2019, month, dayOfMonth, 14, 0);
    }

    public static DepromeetSessionType latest(final LocalDateTime localDateTime) {
        return Stream.of(values())
                .filter(value -> value.getStartedAt().isBefore(localDateTime))
                .reduce(MARCH_SIXTEENTH, (a, b) -> a.isAfter(b) ? a : b);
    }

    public static DepromeetSessionType from(Period period) {
        return Stream.of(values())
                .filter(value -> period.contains(value.getStartedAt()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    private boolean isAfter(DepromeetSessionType anotherSessionType) {
        return this.getStartedAt().isAfter(anotherSessionType.getStartedAt());
    }
}
