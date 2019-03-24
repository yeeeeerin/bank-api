package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.vo.SocialMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final SocialFetchService socialFetchService;
    private final MemberRepository memberRepository;


    @Override
    public void createMember(TokenDto dto) {

        SocialMemberVo memberVo = socialFetchService.getSocialUserInfo(dto);

        Member member = new Member();
        member.setName(memberVo.getUserName());
        member.setProfileHref(memberVo.getProfileHref());
        member.setSocialId(memberVo.getId());

        memberRepository.save(member);

    }
}
