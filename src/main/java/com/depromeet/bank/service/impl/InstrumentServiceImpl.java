package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.instrument.InstrumentFactory;
import com.depromeet.bank.domain.instrument.SettlementStatus;
import com.depromeet.bank.dto.InstrumentExpirationType;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.BadRequestException;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.InstrumentRepository;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.service.InstrumentService;
import com.depromeet.bank.service.TransactionService;
import com.depromeet.bank.vo.AdjustmentRuleValue;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final InstrumentFactory instrumentFactory;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Override
    @Transactional(readOnly = true)
    public List<Instrument> getInstruments(Pageable pageable, InstrumentExpirationType expirationType) {
        switch (expirationType) {
            case ALL:
                return instrumentRepository.findAll(pageable)
                        .stream()
                        .collect(Collectors.toList());
            case TRUE:
                return instrumentRepository.findByExpiredAtLessThan(LocalDateTime.now(), pageable)
                        .stream()
                        .collect(Collectors.toList());
            case FALSE:
                return instrumentRepository.findByExpiredAtGreaterThan(LocalDateTime.now(), pageable)
                        .stream()
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("'expirationType' is not supported. type:" + expirationType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instrument> getInstrumentsNeedToBeSettled(LocalDateTime localDateTime) {
        return instrumentRepository.findByToBeSettledAtLessThanAndSettlementStatus(localDateTime, SettlementStatus.INCOMPLETE);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Instrument> getInstrument(Long instrumentId) {
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        return instrumentRepository.findById(instrumentId);
    }

    @Override
    @Transactional
    public Instrument createInstrument(InstrumentValue instrumentValue, List<AdjustmentRuleValue> adjustmentRuleValues) {
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        if (CollectionUtils.isEmpty(adjustmentRuleValues)) {
            throw new IllegalArgumentException("'adjustmentRuleValues' must not be null or empty. values:" + adjustmentRuleValues);
        }

        Instrument instrument = instrumentFactory.createInstrument(instrumentValue, adjustmentRuleValues);
        return instrumentRepository.save(instrument);
    }

    @Override
    @Transactional
    public Instrument joinInstrument(Long memberId, Long instrumentId, Long investment) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        Assert.notNull(investment, "'investment' must not be null");

        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new NotFoundException("상품이 존재하지 않습니다."));

        if (LocalDateTime.now().isAfter(instrument.getExpiredAt())) {
            throw new BadRequestException("유효기간이 지난 상품이므로 가입할 수 없습니다.");
        }

        Account account = accountService.createAccountForInstrument(memberId, instrument);
        List<Account> instrumentAccounts = instrument.getAccounts();

        boolean hasAlreadyJoined = instrumentAccounts.stream()
                .map(Account::getMember)
                .map(Member::getId)
                .anyMatch(memberId::equals);

        if (hasAlreadyJoined) {
            throw new BadRequestException("이미 가입한 상품은 가입할 수 없습니다.");
        }

        instrumentAccounts.add(account);

        Account defaultAccount = accountService.getDefaultAccount(memberId);

        TransactionRequest transactionRequest = TransactionRequest.forInstrument(
                defaultAccount.getId(),
                account.getId(),
                investment
        );
        transactionService.createTransaction(memberId, transactionRequest);
        return instrumentRepository.save(instrument);
    }

    @Override
    @Transactional
    public Instrument updateInstrument(Long instrumentId, InstrumentValue instrumentValue) {
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        Assert.notNull(instrumentValue, "'instrumentValue' must not be null");
        Instrument instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow(() -> new NotFoundException("상품이 없습니다. "));
        return instrument.update(instrumentValue);
    }

    @Override
    @Transactional
    public void deleteInstrument(Long instrumentId) {
        Assert.notNull(instrumentId, "'instrumentId' must not be null");
        instrumentRepository.findById(instrumentId)
                .ifPresent(instrumentRepository::delete);
    }
}
