package com.depromeet.bank.controller;

import com.depromeet.bank.domain.data.attendance.Attendance;
import com.depromeet.bank.dto.AttendanceResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DataSynchronizationController {

    private final AttendanceService attendanceService;

    @PostMapping("/attendances/{attendanceId:\\d+}/sync")
    public ResponseDto<AttendanceResponse> synchronizeAttendance(@PathVariable Long attendanceId) {

        Attendance attendance = attendanceService.synchronize(attendanceId);
        return ResponseDto.of(
                HttpStatus.OK,
                "출석 정보 동기화에 성공했습니다.",
                AttendanceResponse.from(attendance)
        );
    }

}
