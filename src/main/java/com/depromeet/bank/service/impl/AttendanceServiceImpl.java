package com.depromeet.bank.service.impl;

import com.depromeet.bank.adaptor.google.CellAddress;
import com.depromeet.bank.adaptor.google.GoogleApiAdapter;
import com.depromeet.bank.adaptor.google.GoogleApiResponse;
import com.depromeet.bank.adaptor.google.Range;
import com.depromeet.bank.domain.attendance.Attendance;
import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import com.depromeet.bank.exception.GoogleApiFailedException;
import com.depromeet.bank.repository.AttendanceRepository;
import com.depromeet.bank.service.AttendanceService;
import com.depromeet.bank.vo.AttendanceValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void fetch(DepromeetSessionType depromeetSessionType) {
        Range range = Range.of(
                CellAddress.of(depromeetSessionType.getColumnName(), ROW_NUMBER_FROM),
                CellAddress.of(depromeetSessionType.getColumnName(), ROW_NUMBER_TO)
        );
        List<String> signs = googleApiAdapter.findBySheetIdAndRange(sheetId, range)
                .map(GoogleApiResponse::getSheets)
                .map(sheets -> sheets.get(0))
                .map(GoogleApiResponse.Sheet::getData)
                .map(dataList -> dataList.get(0))
                .map(GoogleApiResponse.SheetData::getRowData)
                .map(rowValuesList -> rowValuesList.stream()
                            .map(GoogleApiResponse.RowValues::getValues)
                            .map(rowValueList -> rowValueList.get(0))
                            .map(GoogleApiResponse.RowValue::getFormattedValue)
                            .collect(Collectors.toList()))
                .orElseThrow(() -> new GoogleApiFailedException("구글 API 요청에 실패했습니다."));
        AttendanceValue attendanceValue = AttendanceValue.from(signs);
        createOrUpdate(attendanceValue, depromeetSessionType);
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
}
