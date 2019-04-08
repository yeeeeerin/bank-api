package com.depromeet.bank.dto;

import com.depromeet.bank.domain.attendance.Attendance;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttendanceResponse {
    private Long id;
    private Integer numberOfAttendee;
    private Integer numberOfAbsentee;
    private String sessionName;
    private LocalDateTime startedAt;

    public static AttendanceResponse from(Attendance attendance) {
        Assert.notNull(attendance, "'attendance' must not be null");
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getNumberOfAttendee(),
                attendance.getNumberOfAbsentee(),
                attendance.getSessionName(),
                attendance.getStartedAt()
        );
    }
}
