package com.depromeet.bank.helper;


import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.InstrumentRequest;
import com.depromeet.bank.vo.SocialMemberVo;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

    public static Account createAccount(String name, Long balance, Double rate, Member member) {
        return Account.builder()
                .name(name)
                .balance(balance)
                .rate(rate)
                .member(member)
                .build();
    }

    public static InstrumentRequest createInstrumentRequest(String name, String description, LocalDateTime expiredAt) {
        InstrumentRequest instrumentRequest = new InstrumentRequest();
        ReflectionTestUtils.setField(instrumentRequest, "name", name);
        ReflectionTestUtils.setField(instrumentRequest, "description", description);
        ReflectionTestUtils.setField(instrumentRequest, "expiredAt", expiredAt);
        return instrumentRequest;
    }
}
