package com.depromeet.bank.vo;

import com.depromeet.bank.domain.data.attendance.AttendanceSign;
import lombok.Value;

import java.util.List;

@Value
public class AttendanceValue {
    private final int numberOfAttendee;
    private final int numberOfAbsentee;

    private AttendanceValue(int numberOfAttendee, int numberOfAbsentee) {
        this.numberOfAttendee = numberOfAttendee;
        this.numberOfAbsentee = numberOfAbsentee;
    }

    public static AttendanceValue of(int numberOfAttendee, int numberOfAbsentee) {
        return new AttendanceValue(numberOfAttendee, numberOfAbsentee);
    }

    public static AttendanceValue from(List<String> formattedSigns) {
        long numberOfAttendee = formattedSigns.stream()
                .filter(AttendanceSign.PRESENT::equalsIgnoreCase)
                .count();
        long numberOfAbsentee = formattedSigns.stream()
                .filter(AttendanceSign.ABSENT::equalsIgnoreCase)
                .count();
        return AttendanceValue.of((int) numberOfAttendee, (int) numberOfAbsentee);
    }
}
