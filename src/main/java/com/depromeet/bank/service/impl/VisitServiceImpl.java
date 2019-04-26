package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.Visitor;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NoContentException;
import com.depromeet.bank.repository.VisitorRepository;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.service.TransactionService;
import com.depromeet.bank.service.VisitService;
import com.depromeet.bank.util.DateTimeUtils;
import com.depromeet.bank.vo.VisitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("visitService")
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private static final long DEFAULT_ATTENDANCE_POINT = 10000L;

    private final VisitorRepository visitorRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public VisitValue attend(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTimeOfDay = DateTimeUtils.getStartTimeOfDay(now);
        LocalDateTime endTimeOfDay = DateTimeUtils.getEndTimeOfDay(now);

        List<Visitor> todayVisitors = visitorRepository.findByCreatedAtGreaterThanAndCreatedAtLessThan(startTimeOfDay, endTimeOfDay);
        int myOrderOfTodayVisitors = todayVisitors.size() + 1;

        boolean hasVisited = todayVisitors.stream()
                .map(Visitor::getMemberId)
                .anyMatch(memberId::equals);

        if (hasVisited) {
            throw new NoContentException();
        }

        boolean isBonusOrder = myOrderOfTodayVisitors % 10 == 0;

        // 연속출석 여부 계산
        List<Visitor> visitedLogs = visitorRepository.findByMemberId(memberId);
        long numberOfContinuousDays = calculateContinuousDays(visitedLogs);
        long point = calculatePoint(DEFAULT_ATTENDANCE_POINT, numberOfContinuousDays, isBonusOrder);

        // 출석 포인트 증정
        Account account = accountService.getDefaultAccount(memberId);
        TransactionRequest transactionRequest = TransactionRequest.forAttendancePoint(account.getId(), point);
        transactionService.createTransaction(Member.SYSTEM_MEMBER_ID, transactionRequest);

        // 출석 기록 생성
        Visitor visitor = Visitor.of(memberId, myOrderOfTodayVisitors, point);
        visitorRepository.save(visitor);

        return VisitValue.of(
                point,
                numberOfContinuousDays,
                myOrderOfTodayVisitors,
                isBonusOrder
        );
    }

    private long calculatePoint(long defaultPoint, long numberOfContinuousDays, boolean isBonusOrder) {
        long bonusRate = isBonusOrder ? 2L : 1L;
        return defaultPoint * (numberOfContinuousDays + 1) * bonusRate;
    }

    private long calculateContinuousDays(List<Visitor> visitors) {
        final List<Visitor> orderedVisitors = new ArrayList<>(visitors);
        // 날짜 역순으로 정렬
        orderedVisitors.sort((source, target) -> {
            LocalDateTime sourceTime = source.getCreatedAt();
            LocalDateTime targetTime = target.getCreatedAt();
            return sourceTime.compareTo(targetTime) * (-1);
        });
        // 어제부터 하루씩 거슬러 올라가면서, 며칠동안 연속 출석인지 계산
        final LocalDateTime now = LocalDateTime.now();
        long numberOfContinuousDays = 0;
        for (Visitor orderedVisitor : orderedVisitors) {
            if (!DateTimeUtils.contains(orderedVisitor.getCreatedAt(),
                    DateTimeUtils.getStartTimeOfDay(now.minusDays(numberOfContinuousDays + 1)),
                    DateTimeUtils.getEndTimeOfDay(now.minusDays(numberOfContinuousDays + 1)))) {
                break;
            }
            numberOfContinuousDays += 1;
        }
        return numberOfContinuousDays;
    }
}
