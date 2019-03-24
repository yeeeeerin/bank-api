package com.depromeet.bank.service.impl;


import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    private TokenDto tokenDto;

    @Before
    public void setup() {
        this.tokenDto = new TokenDto( "KeA8P6SgKrzQM2rs0XQ7oybYyJndSAR9Qxk7sgopyNoAAAFpr8kSpg");
    }

    @Test
    public void createMemberTest(){
        memberService.createMember(tokenDto);
        Member member = memberRepository.findByName("이예린").get();

        assertThat(member.getName(),is("이예린"));

    }
}