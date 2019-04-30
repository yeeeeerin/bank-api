package com.depromeet.bank.util;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {


    private DateTimeUtils() {
    }

    public static LocalDateTime parseFromDataTime(String dataTime) {
        Assert.notNull(dataTime, "'dataTime' must not be null");
        return LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDateTime getStartTimeOfDay(LocalDateTime localDateTime) {
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();
        return LocalDateTime.of(year, month, dayOfMonth, 0, 0);
    }

    public static LocalDateTime getEndTimeOfDay(LocalDateTime localDateTime) {
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();
        return LocalDateTime.of(year, month, dayOfMonth, 0, 0).plusDays(1L);
    }

    public static boolean contains(LocalDateTime source, LocalDateTime from, LocalDateTime to) {
        return (source.isEqual(from) || source.isAfter(from))
                && (source.isEqual(to) || source.isBefore(to));
    }
}
