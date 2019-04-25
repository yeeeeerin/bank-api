package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.Visitor;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.repository.VisitorRepository;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.service.TransactionService;
import com.depromeet.bank.service.VisitService;
import com.depromeet.bank.vo.VisitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("testVisitService")
@RequiredArgsConstructor
public class TestVisitService implements VisitService {
    private static final Long DEFAULT_ATTENDANCE_POINT = 10000L;
    private static final Random random = new Random();

    private final VisitorRepository visitorRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public VisitValue attend(Long memberId) {
        int numberOfVisitor = random.nextInt(10) + 1;
        boolean isBonusOrder = numberOfVisitor % 10 == 0;

        // 연속출석 여부 계산
        long numberOfContinuousDays = random.nextInt(5);
        long point = calculatePoint(DEFAULT_ATTENDANCE_POINT, numberOfContinuousDays, isBonusOrder);

        // 출석 포인트 증정
        Account account = accountService.getDefaultAccount(memberId);
        TransactionRequest transactionRequest = TransactionRequest.forAttendancePoint(account.getId(), point);
        transactionService.createTransaction(Member.SYSTEM_MEMBER_ID, transactionRequest);

        // 출석 기록 생성
        Visitor visitor = Visitor.of(memberId, numberOfVisitor, point);
        visitorRepository.save(visitor);

        return VisitValue.of(
                point,
                numberOfContinuousDays,
                numberOfVisitor,
                isBonusOrder
        );
    }

    private long calculatePoint(long defaultPoint, long numberOfContinuousDays, boolean isBonusOrder) {
        long bonusRate = isBonusOrder ? 2L : 1L;
        return defaultPoint * (numberOfContinuousDays + 1) * bonusRate;
    }
}
