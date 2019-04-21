package com.depromeet.bank.service.impl;

import com.depromeet.bank.adapter.mail.MailAdapter;
import com.depromeet.bank.adapter.mail.MailValue;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.account.AccountFactory;
import com.depromeet.bank.domain.account.JwtFactory;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.helper.DepromeetMembers;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.vo.MemberValue;
import com.depromeet.bank.vo.SocialMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final SocialFetchService socialFetchService;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final JwtFactory jwtFactory;
    private final DepromeetMembers depromeetMembers;
    private final MailAdapter mailAdapter;

    @Override
    @Transactional
    public String createMember(TokenDto dto) {

        SocialMemberVo memberVo = socialFetchService.getSocialUserInfo(dto);

        Member member = memberRepository.findBySocialId(memberVo.getUserId())
                .orElseGet(() -> {
                    // 멤버 생성
                    Member createdMember = new Member();
                    String name = memberVo.getUserName();
                    createdMember.setName(name);
                    depromeetMembers.getNumberByName(name).ifPresent(createdMember::setCardinalNumber);

                    createdMember.setProfileHref(memberVo.getProfileHref());
                    createdMember.setSocialId(memberVo.getId());
                    memberRepository.save(createdMember);

                    // 계좌 생성
                    Account account = accountFactory.createForMember(createdMember, AccountDto.initAccount());
                    accountRepository.save(account);

                    // 메일 발송
                    mailAdapter.send(MailValue.of(
                            "[디프가즈아] 신규 회원 가입 알림 - " + name,
                            name + "님이 디프가즈아 서비스에 가입하셨습니다 :D",
                            Collections.singletonList("depromeet.billionaire@gmail.com"
                            )));
                    return createdMember;
                });

        return jwtFactory.generateToken(member);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMembers(Pageable pageable) {
        Assert.notNull(pageable, "'pageable' must not be null");
        return memberRepository.findAll(pageable).stream()
                .filter(member -> !member.getId().equals(Member.SYSTEM_MEMBER_ID))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");
        return memberRepository.findById(memberId);
    }

    @Override
    @Transactional
    public Member updateMember(Long memberId, MemberValue memberValue) {
        Assert.notNull(memberId, "'memberId' must not be null");
        Assert.notNull(memberValue, "'memberValue' must not be null");

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다. "));
        member.update(memberValue);
        return memberRepository.save(member);
    }

}
