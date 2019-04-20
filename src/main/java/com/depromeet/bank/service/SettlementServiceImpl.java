package com.depromeet.bank.service;


import com.depromeet.bank.domain.data.attendance.Attendance;
import com.depromeet.bank.domain.data.attendance.DepromeetSessionType;
import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.rule.*;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.depromeet.bank.domain.Member.SYSTEM_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {
    private final InstrumentRepository instrumentRepository;
    private final AttendanceService attendanceService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public Instrument updateRuleIsSatisfied(Long instrumentId) {
        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다. "));
        List<AdjustmentRule> rules = instrument.getAdjustmentRules()
                .stream()
                .filter(rule -> {
                    Condition condition = rule.getCondition();
                    Integer data = this.getData(condition);
                    return this.doesSatisfy(condition, data);
                })
                .peek(rule -> rule.setSatisfied(true))
                .collect(Collectors.toList());
        return instrumentRepository.save(instrument);
    }

    @Override
    @Transactional
    public void settle(Long instrumentId) {
        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다. "));
        Double rate = instrument.getAdjustmentRules()
                .stream()
                .filter(AdjustmentRule::isSatisfied)
                .map(AdjustmentRule::getReward)
                .map(Reward::getRate)
                .reduce(0.0, Double::sum);
        instrument.getAccounts().forEach(account -> transactionService.createTransaction(
                SYSTEM_MEMBER_ID,
                TransactionRequest.forSettlement(account.getId(), account.getBalance(), rate)
        ));
        instrument.setAsCompleted();
        instrumentRepository.save(instrument);
    }

    private Integer getData(Condition condition) {
        switch (condition.getDataType()) {
            case NUMBER_OF_ATTENDEE:
                Period period = condition.getPeriod();
                DepromeetSessionType depromeetSessionType = DepromeetSessionType.from(period);
                Attendance attendance = attendanceService.findBySessionType(depromeetSessionType)
                        .orElseGet(() -> attendanceService.fetch(depromeetSessionType));
                return attendance.getNumberOfAttendee();
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("'dataType' is not supported. dataType:" + condition.getDataType());
        }
    }

    private boolean doesSatisfy(Condition condition, Integer data) {
        boolean result = false;

        ComparisonType comparisonType = condition.getComparisonType();
        Long goal = condition.getGoal();
        switch (comparisonType) {
            case GREATER_THAN:
                result = data.longValue() > goal;
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                result = data.longValue() >= goal;
                break;
            case EQUAL_TO:
                result = data.longValue() == goal;
                break;
            case LESS_THAN_OR_EQUAL_TO:
                result = data.longValue() <= goal;
                break;
            case LESS_THAN:
                result = data.longValue() < goal;
                break;
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("'comparisonType' is not supported. supportedType:" + comparisonType);
        }

        NotType notType = condition.getNotType();
        switch (notType) {
            case POSITIVE:
                result = result;
                break;
            case NEGATIVE:
                result = !result;
                break;
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("'notType' is not supported. notType:" + notType);
        }

        // FIXME: 사건이 여러개인 경우도 구현해야함
        return result;
    }
}
