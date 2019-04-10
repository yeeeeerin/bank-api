package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.account.KakaoProviders;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.vo.SocialMemberVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(MockitoJUnitRunner.class)
public class SocialFetchServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    private SocialFetchServiceImpl service;
    private TokenDto tokenDto;

    @Before
    public void setup() {
        this.service = new SocialFetchServiceImpl(restTemplate);
        this.tokenDto = new TokenDto("KeA8P6SgKrzQM2rs0XQ7oybYyJndSAR9Qxk7sgopyNoAAAFpr8kSpg");
    }

    @Test(expected = HttpClientErrorException.class)
    public void 카카오_토큰이_유효하지_않은경우__HttpClientErrorException_예외_발생() {
        // given
        when(restTemplate.exchange(eq(KakaoProviders.KAKAO.getUserinfoEndpoint()),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SocialMemberVo.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        // when
        SocialMemberVo socialMemberVo = service.getSocialUserInfo(this.tokenDto);
        // then
        assert false;
    }

    @Test
    public void 카카오_토큰이_유효한경우__카카오_회원정보를_돌려주어야함() {
        // given
        SocialMemberVo socialMemberVo = TestHelper.createSocialMemberVo(1L, "이예린", "profileImageUrl");
        ResponseEntity<SocialMemberVo> responseEntity = new ResponseEntity<>(socialMemberVo, HttpStatus.OK);
        when(restTemplate.exchange(eq(KakaoProviders.KAKAO.getUserinfoEndpoint()),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SocialMemberVo.class)))
                .thenReturn(responseEntity);
        // when
        SocialMemberVo result = service.getSocialUserInfo(this.tokenDto);
        // then
        assertNotNull(result);
        assertThat(result.getId(), is(1L));
        assertThat(result.getUserName(), is("이예린"));
        assertThat(result.getProfileHref(), is("profileImageUrl"));
    }
}