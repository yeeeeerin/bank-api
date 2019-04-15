package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.domain.instrument.InstrumentFactory;
import com.depromeet.bank.domain.instrument.SettlementStatus;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.exception.InternalServerErrorException;
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
    public List<Instrument> getInstruments(Pageable pageable) {
        return instrumentRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instrument> getInstrumentsExpiredAndIncomplete(LocalDateTime localDateTime) {
        return instrumentRepository.findByExpiredAtLessThanAndSettlementStatus(localDateTime, SettlementStatus.INCOMPLETE);
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

        Account account = accountService.createAccountForInstrument(memberId, instrument);
        List<Account> accounts = instrument.getAccounts();
        accounts.add(account);

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
