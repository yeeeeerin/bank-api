package com.depromeet.bank.dto;

import com.depromeet.bank.vo.RankValue;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
@ToString
public class RankResponse {

    private MemberResponse member;
    private Long assetValue;
    private Integer rankNumber;

    private RankResponse(MemberResponse member, Long balance, Integer rankNumber) {
        this.member = Objects.requireNonNull(member);
        this.assetValue = Objects.requireNonNull(balance);
        this.rankNumber = Objects.requireNonNull(rankNumber);
    }

    public static RankResponse from(RankValue rankValue) {
        Assert.notNull(rankValue, "'rankValue' must not be null");

        return new RankResponse(
                MemberResponse.from(rankValue.getMember()),
                rankValue.getAssetValue(),
                rankValue.getRankNumber()
        );
    }
}
