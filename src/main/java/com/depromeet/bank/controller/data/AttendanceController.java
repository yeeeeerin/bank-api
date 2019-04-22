package com.depromeet.bank.controller.data;

import com.depromeet.bank.dto.AttendanceResponse;
import com.depromeet.bank.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/attendances")
    public List<AttendanceResponse> getAttendances(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return attendanceService.getAttendances(pageable)
                .stream()
                .map(AttendanceResponse::from)
                .collect(Collectors.toList());
    }
}
