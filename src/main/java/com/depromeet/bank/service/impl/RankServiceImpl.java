package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.RankService;
import com.depromeet.bank.vo.RankValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private final MemberService memberService;
    private final AccountService accountService;

    @Override
    @Transactional(readOnly = true)
    public List<RankValue> getRankedMembers() {
        return memberService.getMembers(PageRequest.of(0, 1000))
                .stream()
                .map(member -> RankValue.of(
                        member,
                        accountService.getAccount(member.getId(), 0)
                                .stream()
                                .map(Account::getBalance)
                                .reduce(0L, Long::sum)
                ))
                .sorted((a, b) -> (-1) * a.compareTo(b))
                .collect(Collectors.toList());
    }
}
