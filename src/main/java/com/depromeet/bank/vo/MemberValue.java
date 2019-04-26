package com.depromeet.bank.vo;

import com.depromeet.bank.dto.MemberUpdateRequest;
import lombok.Value;
import org.springframework.util.Assert;

@Value
public class MemberValue {
    private final String name;
    private final Integer cardinalNumber;
    private final String profileImageUrl;

    private MemberValue(String name,
                        Integer cardinalNumber,
                        String profileImageUrl) {
        this.name = name;
        this.cardinalNumber = cardinalNumber;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberValue from(MemberUpdateRequest memberUpdateRequest) {
        Assert.notNull(memberUpdateRequest, "'memberUpdateRequest' must not be null");

        return new MemberValue(
                memberUpdateRequest.getName(),
                memberUpdateRequest.getCardinalNumber(),
                memberUpdateRequest.getProfileImageUrl()
        );
    }
}
