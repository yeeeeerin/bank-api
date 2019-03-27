package com.depromeet.bank.dto;

import com.depromeet.bank.domain.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Getter
@ToString
public class MemberResponse {
    private Long id;
    private String name;
    @Nullable
    private String profileImageUrl;

    private MemberResponse(Long id, String name, @Nullable String profileImageUrl) {
        Assert.notNull(id, "'id' must not be null");
        Assert.notNull(name, "'name' must not be null");

        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public static MemberResponse from(Member member) {
        Assert.notNull(member, "'member' must not be null");

        Long memberId = member.getId();
        String name = member.getName();
        String profileImageUrl = member.getProfileHref();

        return new MemberResponse(memberId, name, profileImageUrl);
    }
}
