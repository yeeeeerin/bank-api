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

}
