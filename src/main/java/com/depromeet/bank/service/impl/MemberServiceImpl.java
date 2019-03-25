package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.utils.JwtFactory;
import com.depromeet.bank.vo.SocialMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final SocialFetchService socialFetchService;
    private final MemberRepository memberRepository;
    private final JwtFactory jwtFactory;

    @Override
    public String createMember(TokenDto dto) {

        SocialMemberVo memberVo = socialFetchService.getSocialUserInfo(dto);


        Member member;

        Optional<Member> memberOptional = memberRepository.findBySocialId(memberVo.getUserId());
        if(memberOptional.isPresent()){
            member = memberOptional.get();
        }else {
            member = new Member();
            member.setName(memberVo.getUserName());
            member.setProfileHref(memberVo.getProfileHref());
            member.setSocialId(memberVo.getId());
        }

        memberRepository.save(member);

        String jwt = jwtFactory.generateToken(member);

        return jwt;

    }


}
