package com.depromeet.bank.helper;


import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.vo.SocialMemberVo;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

public final class TestHelper {
    // nontinstantiable utility class
    private TestHelper() {
    }

    public static SocialMemberVo createSocialMemberVo(Long id, String nickname, String imageUrl) {
        SocialMemberVo socialMemberVo = new SocialMemberVo();
        ReflectionTestUtils.setField(socialMemberVo, "id", id);
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("nickname", nickname);
        propertyMap.put("profile_image", imageUrl);
        ReflectionTestUtils.setField(socialMemberVo, "properties", propertyMap);
        return socialMemberVo;
    }

    public static Member createMember(Long socialId, String name, String profileHref) {
        Member member = new Member();
        member.setSocialId(socialId);
        member.setName(name);
        member.setProfileHref(profileHref);
        return member;
    }

    public static Account createAccount(String name, Integer balance, Double rate) {
        return Account.builder()
                .name(name)
                .balance(balance)
                .rate(rate)
                .build();
    }
}
