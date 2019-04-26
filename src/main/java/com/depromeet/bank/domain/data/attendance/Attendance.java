package com.depromeet.bank.domain.data.attendance;

import com.depromeet.bank.vo.AttendanceValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;
    private Integer numberOfAttendee;
    private Integer numberOfAbsentee;
    private String sessionName;
    private LocalDateTime startedAt;

    public static Attendance of(AttendanceValue attendanceValue, DepromeetSessionType depromeetSessionType) {
        int numberOfAttendee = attendanceValue.getNumberOfAttendee();
        int numberOfAbsentee = attendanceValue.getNumberOfAbsentee();
        String sessionName = depromeetSessionType.name();
        LocalDateTime startedAt = depromeetSessionType.getStartedAt();
        return new Attendance(null, numberOfAttendee, numberOfAbsentee, sessionName, startedAt);
    }

    public Attendance update(AttendanceValue attendanceValue) {
        if (attendanceValue == null) {
            return this;
        }
        this.numberOfAttendee = attendanceValue.getNumberOfAttendee();
        this.numberOfAbsentee = attendanceValue.getNumberOfAbsentee();
        return this;
    }
}
