package com.depromeet.bank.integration;

import com.depromeet.bank.adapter.mail.MailAdapter;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.account.AccountType;
import com.depromeet.bank.domain.account.JwtFactory;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.vo.SocialMemberVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class MemberAttendTest {
    private static final String AUTHORIZATION_TOKEN = "Bearer authorizationToken";

    @MockBean
    private JwtFactory jwtFactory;
    @MockBean
    private SocialFetchService socialFetchService;
    @MockBean
    private MailAdapter mailAdapter;    // 테스트 실행될 때 메일을 발송하지 않습니다.
    @Mock
    private SocialMemberVo socialMemberVo;

    @SpyBean
    private MemberRepository memberRepository;
    @SpyBean
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Long memberId;

    @Before
    public void setUp() throws Exception {
        TestHelper.createSystemMember(memberRepository, accountRepository);

        // 회원 생성
        when(socialFetchService.getSocialUserInfo(isA(TokenDto.class))).thenReturn(socialMemberVo);
        when(socialMemberVo.getId()).thenReturn(1000L);
        when(socialMemberVo.getUserName()).thenReturn("userName");
        when(socialMemberVo.getProfileHref()).thenReturn("profileHref");
        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(new TokenDto(""))))
                .andExpect(status().isOk());
        memberId = memberRepository.findAll().get(0).getId();
        // 인증 토큰
        when(jwtFactory.getMemberId(AUTHORIZATION_TOKEN)).thenReturn(Optional.of(memberId));
    }

    @Test
    public void 멤버가_처음_출석하면_포인트를_받아야함() throws Exception {
        // given
        // when
        mockMvc.perform(post("/api/members/me/attend")
                .header("authorization", AUTHORIZATION_TOKEN))
                // then
                .andExpect(status().isOk());
    }
}
