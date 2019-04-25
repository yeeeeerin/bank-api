package com.depromeet.bank.service.impl;

import com.depromeet.bank.adapter.openapi.AirGrade;
import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.domain.data.airinfo.AirInfo;
import com.depromeet.bank.domain.data.message.Message;
import com.depromeet.bank.repository.AirInfoRepository;
import com.depromeet.bank.repository.AttendanceRepository;
import com.depromeet.bank.repository.MessageRepository;
import com.depromeet.bank.service.GraphService;
import com.depromeet.bank.util.DateTimeUtils;
import com.depromeet.bank.vo.GraphValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService {
    private final AttendanceRepository attendanceRepository;
    private final AirInfoRepository airInfoRepository;
    private final MessageRepository messageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GraphValue> getDepromeetSessionData() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("startedAt").descending());
        return attendanceRepository.findAll(pageable)
                .stream()
                .map(GraphValue::from)
                .sorted(Comparator.comparing(GraphValue::getTime))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GraphValue> getAirPollutionData() {
        LocalDateTime aWeekAgo = LocalDateTime.now().minusDays(6L);
        // 최근 일주일의 데이터를 가져온다 (오늘포함, 6일전 데이터부터 가져오기)
        // 하루 단위로 평균을 구한다
        Map<LocalDateTime, List<AirInfo>> airInfoMap = new HashMap<>();
        airInfoRepository.findByStationNameAndDataTimeGreaterThan(OpenApiStationName.SEOUL, aWeekAgo)
                .forEach(airInfo -> {
                    LocalDateTime key = DateTimeUtils.getStartTimeOfDay(airInfo.getDataTime());
                    airInfoMap.computeIfAbsent(key, k -> new ArrayList<>());
                    airInfoMap.get(key).add(airInfo);
                });
        return airInfoMap.entrySet()
                .stream()
                .map(entry -> {
                    LocalDateTime time = entry.getKey();
                    List<AirInfo> airInfos = entry.getValue();
                    AirGrade airGrade = AirGrade.from(
                            airInfos.stream()
                                    .map(AirInfo::getAirGrade)
                                    .map(AirGrade::getValue)
                                    .reduce(0, Integer::sum) / airInfos.size()
                    );
                    return GraphValue.of(time, airGrade.getValue().longValue());
                })
                .sorted(Comparator.comparing(GraphValue::getTime))
                .collect(Collectors.toList());
    }

    /**
     * 오늘을 제외하고, 8일전부터 어제까지의 데이터를 보여줍니다.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GraphValue> getKakaotalkData() {
        LocalDateTime to = DateTimeUtils.getStartTimeOfDay(LocalDateTime.now());
        LocalDateTime from = to.minusDays(7);
        Map<LocalDateTime, List<Message>> messageMap = new HashMap<>();
        messageRepository.findByWrittenAtGreaterThanAndWrittenAtLessThan(from, to)
                .forEach(message -> {
                    LocalDateTime key = DateTimeUtils.getStartTimeOfDay(message.getWrittenAt());
                    messageMap.computeIfAbsent(key, k -> new ArrayList<>());
                    messageMap.get(key).add(message);
                });
        return messageMap.entrySet()
                .stream()
                .map(entry -> GraphValue.of(entry.getKey(), (long) entry.getValue().size()))
                .sorted(Comparator.comparing(GraphValue::getTime))
                .collect(Collectors.toList());
    }
}
