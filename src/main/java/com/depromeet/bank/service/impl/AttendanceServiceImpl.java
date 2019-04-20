package com.depromeet.bank.service.impl;

import com.depromeet.bank.adapter.google.CellAddress;
import com.depromeet.bank.adapter.google.GoogleApiAdapter;
import com.depromeet.bank.adapter.google.GoogleApiResponse;
import com.depromeet.bank.adapter.google.Range;
import com.depromeet.bank.domain.data.attendance.Attendance;
import com.depromeet.bank.domain.data.attendance.DepromeetSessionType;
import com.depromeet.bank.exception.GoogleApiFailedException;
import com.depromeet.bank.exception.InternalServerErrorException;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.AttendanceRepository;
import com.depromeet.bank.service.AttendanceService;
import com.depromeet.bank.vo.AttendanceValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@PropertySource("classpath:depromeet.properties")
public class AttendanceServiceImpl implements AttendanceService {

    private static final int ROW_NUMBER_FROM = 4;
    private static final int ROW_NUMBER_TO = 85;

    private final GoogleApiAdapter googleApiAdapter;
    private final AttendanceRepository attendanceRepository;
    private final String sheetId;

    public AttendanceServiceImpl(GoogleApiAdapter googleApiAdapter,
                                 AttendanceRepository attendanceRepository,
                                 @Value("${depromeet.attendance.spread-sheet-id}") String sheetId) {
        this.googleApiAdapter = googleApiAdapter;
        this.attendanceRepository = attendanceRepository;
        this.sheetId = sheetId;
    }

    @Override
    @Transactional
    public Attendance fetch(DepromeetSessionType depromeetSessionType) {
        Range range = Range.of(
                CellAddress.of(depromeetSessionType.getColumnName(), ROW_NUMBER_FROM),
                CellAddress.of(depromeetSessionType.getColumnName(), ROW_NUMBER_TO)
        );

        GoogleApiResponse googleApiResponse = googleApiAdapter.findBySheetIdAndRange(sheetId, range)
                .orElseThrow(() -> new GoogleApiFailedException("구글 API 요청에 실패했습니다."));
        List<String> signs = googleApiAdapter.parseAttendanceSigns(googleApiResponse);
        AttendanceValue attendanceValue = AttendanceValue.from(signs);
        return createOrUpdate(attendanceValue, depromeetSessionType);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Attendance> findBySessionType(DepromeetSessionType depromeetSessionType) {
        return attendanceRepository.findBySessionName(depromeetSessionType.name());
    }

    @Override
    @Transactional
    public Attendance createOrUpdate(AttendanceValue attendanceValue, DepromeetSessionType depromeetSessionType) {
        Assert.notNull(depromeetSessionType, "'depromeetSessionType' must not be null");
        Assert.notNull(attendanceValue, "'attendanceValue' must not be null");

        return attendanceRepository.findBySessionName(depromeetSessionType.name())
                .map(attendance -> attendance.update(attendanceValue))
                .orElseGet(() -> {
                    Attendance attendance = Attendance.of(attendanceValue, depromeetSessionType);
                    return attendanceRepository.save(attendance);
                });
    }

    @Override
    @Transactional
    public Attendance synchronize(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException("출석 정보가 존재하지 않습니다."));
        DepromeetSessionType depromeetSessionType = DepromeetSessionType.valueOf(attendance.getSessionName());
        if (depromeetSessionType == null) {
            log.error("depromeetSessionType must not be null. attendanceId:{}", attendanceId);
            throw new InternalServerErrorException("데이터가 올바르지 않습니다.");
        }
        return fetch(depromeetSessionType);
    }
}
