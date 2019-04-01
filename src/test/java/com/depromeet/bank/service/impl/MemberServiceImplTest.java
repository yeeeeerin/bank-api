package com.depromeet.bank.service.impl;


import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.utils.JwtFactory;
import com.depromeet.bank.vo.SocialMemberVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(MockitoJUnitRunner.class)
public class MemberServiceImplTest {

    private static final String MEMBER_NAME = "이예린";
    private static final String MEMBER_PROFILE_HREF = "profileHref";
    private static final long MEMBER_KAKAO_ID = 1L;
    private static final String KAKAO_NICKNAME = "kakaoNickname";
    private static final String KAKAO_PROFILE_IMAGE_URL = "http://somewhere.kakao.com/profile-image";
    private static final String TOKEN_OF_YERIN = "tokenOfYerin";

    @Mock
    private SocialFetchService socialFetchService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtFactory jwtFactory;
    @Mock
    private AccountService accountService;

    private MemberService memberService;
    private TokenDto tokenDto;
    private Member member;

    @Before
    public void setup() {
        memberService = new MemberServiceImpl(
                socialFetchService,
                memberRepository,
                accountService,
                jwtFactory);
        this.tokenDto = new TokenDto("x6n6QvcJhH-nJPHgaasGzgDjUbLofvh-pZjBywopyNkAAAFpskMpHg");
        member = TestHelper.createMember(MEMBER_KAKAO_ID, MEMBER_NAME, MEMBER_PROFILE_HREF);
    }

    @Test(expected = HttpClientErrorException.class)
    public void 카카오_토큰이_유효하지_않은_경우__HttpClientErrorException_예외_발생() {
        // given
        when(socialFetchService.getSocialUserInfo(tokenDto))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        // when
        String result = memberService.createMember(tokenDto);
        // then
        assert false;
    }

    @Test
    public void 카카오_토큰이_유효하고_우리서비스에_처음_가입하는_경우__토큰을_만들어주어야함() {
        // given
        SocialMemberVo socialMemberVo = TestHelper.createSocialMemberVo(MEMBER_KAKAO_ID, KAKAO_NICKNAME, KAKAO_PROFILE_IMAGE_URL);
        when(socialFetchService.getSocialUserInfo(tokenDto)).thenReturn(socialMemberVo);
        when(memberRepository.findBySocialId(MEMBER_KAKAO_ID)).thenReturn(Optional.empty());
        when(jwtFactory.generateToken(any(Member.class))).thenReturn(TOKEN_OF_YERIN);
        // when
        String result = memberService.createMember(tokenDto);
        // then
        assertThat(result, is(TOKEN_OF_YERIN));
    }

    @Test
    public void 카카오_토큰이_유효하고_우리서비스에_이미_가입되어있는_경우__토큰을_만들어주어야함() {

        // given
        SocialMemberVo socialMemberVo = TestHelper.createSocialMemberVo(MEMBER_KAKAO_ID, MEMBER_NAME, MEMBER_PROFILE_HREF);
        when(socialFetchService.getSocialUserInfo(tokenDto)).thenReturn(socialMemberVo);
        when(memberRepository.findBySocialId(MEMBER_KAKAO_ID)).thenReturn(Optional.of(member));
        when(jwtFactory.generateToken(any(Member.class))).thenReturn(TOKEN_OF_YERIN);

        // when
        String result = memberService.createMember(tokenDto);

        // then
        assertThat(result, is(TOKEN_OF_YERIN));
    }


    //todo test코드 작성하기
    @Test
    public void 계정이_생성되면_계좌도_익명으로_같이_생성된다() {
        //given

        //when

        //then
    }


}