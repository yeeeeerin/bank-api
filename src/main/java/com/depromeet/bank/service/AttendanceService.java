package com.depromeet.bank.service;

import com.depromeet.bank.domain.attendance.Attendance;
import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import com.depromeet.bank.vo.AttendanceValue;

import java.util.Optional;

public interface AttendanceService {
    Attendance fetch(DepromeetSessionType depromeetSessionType);

    Optional<Attendance> findBySessionType(DepromeetSessionType depromeetSessionType);

    Attendance createOrUpdate(AttendanceValue attendanceValue, DepromeetSessionType depromeetSessionType);

    Attendance synchronize(Long attendanceId);
}
