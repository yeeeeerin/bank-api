package com.depromeet.bank.repository;

import com.depromeet.bank.domain.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findBySessionName(String sessionName);
}
