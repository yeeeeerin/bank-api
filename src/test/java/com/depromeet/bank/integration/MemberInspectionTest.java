package com.depromeet.bank.integration;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberInspectionTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    public void 실명이고_기수가_존재하면_결과에_포함되지_않음() {
        // given
        Member member = TestHelper.createMember(1L, "전해성", "");
        member.setCardinalNumber(1);
        memberRepository.saveAndFlush(member);
        // when
        Collection<Member> members = memberService.getMembersToBeCorrected();
        // then
        assertThat(members.size()).isEqualTo(0);
    }

    @Test
    public void 실명이_아니고_기수가_존재하는_멤버는_결과에_포함되어야함() {
        // given
        Member member = TestHelper.createMember(1L, "test", "");
        member.setCardinalNumber(1);
        memberRepository.saveAndFlush(member);
        // when
        Collection<Member> members = memberService.getMembersToBeCorrected();
        // then
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.contains(member)).isTrue();
    }

    @Test
    public void 실명이고_기수가_존재하지_않으면_결과에_포함되어야함() {
        // given
        Member member = TestHelper.createMember(1L, "전해성", "");
        memberRepository.saveAndFlush(member);
        // when
        Collection<Member> members = memberService.getMembersToBeCorrected();
        // then
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.contains(member)).isTrue();
    }

    @Test
    public void 실명도_아니고_기수도_존재하지_않으면_결과에_포함되어야함() {
        // given
        Member member = TestHelper.createMember(1L, "test", "");
        memberRepository.saveAndFlush(member);
        // when
        Collection<Member> members = memberService.getMembersToBeCorrected();
        // then
        assertThat(members.size()).isGreaterThanOrEqualTo(1);
        assertThat(members.contains(member)).isTrue();
    }
}
