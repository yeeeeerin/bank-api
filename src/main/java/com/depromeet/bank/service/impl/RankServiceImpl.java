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
        final List<RankValue> rankValues = memberService.getMembers(PageRequest.of(0, 1000))
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
        setRankNumber(rankValues);
        return rankValues;
    }

    private void setRankNumber(List<RankValue> rankValues) {
        // TODO: O(N) 안으로 계산하기. 현재 O(N^2)
        for (RankValue rankValue : rankValues) {
            Long assetValue = rankValue.getAssetValue();
            long rankNumber = rankValues.stream()
                    .map(RankValue::getAssetValue)
                    .filter(value -> value > assetValue)
                    .count();
            rankValue.setRankNumber((int) rankNumber + 1);
        }
    }
}
