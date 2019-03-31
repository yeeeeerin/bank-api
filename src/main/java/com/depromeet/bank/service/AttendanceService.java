package com.depromeet.bank.service;

import com.depromeet.bank.domain.attendance.Attendance;
import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import com.depromeet.bank.vo.AttendanceValue;

import java.util.Optional;

public interface AttendanceService {
    void fetch(DepromeetSessionType depromeetSessionType);

    Optional<Attendance> findBySessionType(DepromeetSessionType depromeetSessionType);

    Attendance createOrUpdate(AttendanceValue attendanceValue, DepromeetSessionType depromeetSessionType);
}
