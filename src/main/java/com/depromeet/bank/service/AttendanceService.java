package com.depromeet.bank.service;

import com.depromeet.bank.domain.data.attendance.Attendance;
import com.depromeet.bank.domain.data.attendance.DepromeetSessionType;
import com.depromeet.bank.vo.AttendanceValue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    Attendance fetch(DepromeetSessionType depromeetSessionType);

    Optional<Attendance> findBySessionType(DepromeetSessionType depromeetSessionType);

    Attendance createOrUpdate(AttendanceValue attendanceValue, DepromeetSessionType depromeetSessionType);

    Attendance synchronize(Long attendanceId);

    List<Attendance> getAttendances(Pageable pageable);
}
