package com.depromeet.bank.vo;

import com.depromeet.bank.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RankValue implements Comparable<RankValue> {
    /**
     * 회원 정보
     */
    private Member member;
    /**
     * 회원의 총 자산
     */
    private Long assetValue;
    /**
     * 순위결과 목록에서 몇 번째인지
     */
    private Integer rankNumber;

    public static RankValue of(Member member, Long assetValue) {
        Assert.notNull(member, "'member' must not be null");
        Assert.notNull(assetValue, "'assetValue' must not be null");

        RankValue rankValue = new RankValue();
        rankValue.member = member;
        rankValue.assetValue = assetValue;
        return rankValue;
    }

    public void setRankNumber(Integer rankNumber) {
        this.rankNumber = rankNumber;
    }

    @Override
    public int compareTo(RankValue target) {
        return this.assetValue.compareTo(target.assetValue);
    }
}
