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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final SocialFetchService socialFetchService;
    private final MemberRepository memberRepository;
    private final JwtFactory jwtFactory;

    @Override
    @Transactional
    public String createMember(TokenDto dto) {

        SocialMemberVo memberVo = socialFetchService.getSocialUserInfo(dto);

        Member member = memberRepository.findBySocialId(memberVo.getUserId())
                .orElseGet(() -> {
                    Member member1 = new Member();
                    member1.setName(memberVo.getUserName());
                    member1.setProfileHref(memberVo.getProfileHref());
                    member1.setSocialId(memberVo.getId());
                    return member1;
                });

        memberRepository.save(member);

        return jwtFactory.generateToken(member);

    }


}
