package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.utils.JwtFactory;
import com.depromeet.bank.vo.SocialMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final SocialFetchService socialFetchService;
    private final MemberRepository memberRepository;
    private final AccountService accountService;
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

        accountService.createAccount(new AccountDto(), member.getId());

        return jwtFactory.generateToken(member);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMembers(Pageable pageable) {
        Assert.notNull(pageable, "'pageable' must not be null");
        return memberRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        return memberRepository.findById(memberId);
    }

}
