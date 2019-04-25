package com.depromeet.bank.vo;

import com.depromeet.bank.domain.data.attendance.Attendance;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class GraphValue {
    private final LocalDateTime time;
    private final Long number;

    public static GraphValue from(Attendance attendance) {
        return new GraphValue(
                attendance.getStartedAt(),
                attendance.getNumberOfAttendee().longValue()
        );
    }
}
