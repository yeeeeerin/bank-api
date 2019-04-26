package com.depromeet.bank.vo;

import com.depromeet.bank.domain.Member;
import lombok.Value;

@Value(staticConstructor = "of")
public class RankValue implements Comparable<RankValue> {
    private final Member member;
    private final Long assetValue;

    @Override
    public int compareTo(RankValue target) {
        return this.assetValue.compareTo(target.assetValue);
    }
}
