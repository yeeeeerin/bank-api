package com.depromeet.bank.integration;

import com.depromeet.bank.domain.account.JwtFactory;
import com.depromeet.bank.dto.MemberUpdateRequest;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.vo.SocialMemberVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class MemberCommandTest {

    private static final String AUTHORIZATION_TOKEN = "Bearer authorizationToken";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @MockBean
    private JwtFactory jwtFactory;
    @MockBean
    private SocialFetchService socialFetchService;

    @Mock
    private SocialMemberVo socialMemberVo;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 멤버_생성_성공() throws Exception {
        // given
        when(socialFetchService.getSocialUserInfo(isA(TokenDto.class))).thenReturn(socialMemberVo);
        when(socialMemberVo.getId()).thenReturn(1000L);
        when(socialMemberVo.getUserName()).thenReturn("userName");
        when(socialMemberVo.getProfileHref()).thenReturn("profileHref");

        // when
        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(new TokenDto(""))))
                // then
                .andExpect(status().isOk());
    }

    @Test
    public void 멤버_업데이트__기수가_잘_변경되는지() throws Exception {
        // given
        final Long memberId = 멤버_생성();
        when(jwtFactory.getMemberId(AUTHORIZATION_TOKEN)).thenReturn(Optional.of(memberId));
        // when
        mockMvc.perform(put("/api/members/{memberId}", memberId)
                .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(createMemberUpdateRequest(1))))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.cardinalNumber").value(1));
    }

    private Long 멤버_생성() throws Exception {
        when(socialFetchService.getSocialUserInfo(isA(TokenDto.class))).thenReturn(socialMemberVo);
        when(socialMemberVo.getId()).thenReturn(1000L);
        when(socialMemberVo.getUserName()).thenReturn("userName");
        when(socialMemberVo.getProfileHref()).thenReturn("profileHref");

        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(new TokenDto(""))))
                .andExpect(status().isOk());

        return memberRepository.findAll().get(0).getId();
    }

    private MemberUpdateRequest createMemberUpdateRequest(Integer cardinalNumber) throws Exception {
        Constructor<MemberUpdateRequest> constructor =
                MemberUpdateRequest.class.getDeclaredConstructor(String.class, Integer.class, String.class);
        constructor.setAccessible(true);
        return constructor.newInstance(null, cardinalNumber, null);
    }
}
