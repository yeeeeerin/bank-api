package com.depromeet.bank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberUpdateRequest {
    private String name;
    private Integer cardinalNumber;
    private String profileImageUrl;

    private MemberUpdateRequest(String name,
                                Integer cardinalNumber,
                                String profileImageUrl) {
        this.name = name;
        this.cardinalNumber = cardinalNumber;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberUpdateRequest of(String name,
                                         Integer cardinalNumber,
                                         String profileImageUrl) {
        return new MemberUpdateRequest(name, cardinalNumber, profileImageUrl);
    }
}
