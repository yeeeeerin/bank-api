package com.depromeet.bank.integration;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.JwtFactory;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class GetMembersTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtFactory jwtFactory;
    @Autowired
    private ObjectMapper objectMapper;

    private Member haeseong;
    private Member yerin;
    private Member jaeyeon;

    @Before
    public void setup() {
        haeseong = TestHelper.createMember(11L, "전해성", "profileOfHaeseong");
        memberRepository.save(haeseong);
        assertThat(memberRepository.findAll().size(), is(1));

        yerin = TestHelper.createMember(12L, "이예린", "profileOfYerin");
        memberRepository.save(yerin);
        assertThat(memberRepository.findAll().size(), is(2));

        jaeyeon = TestHelper.createMember(13L, "김재연", "profileOfJaeyeon");
        memberRepository.save(jaeyeon);
        assertThat(memberRepository.findAll().size(), is(3));
    }

    @Test
    public void 회원_목록_조회__토큰이_유효하지_않은_경우__401_응답() throws Exception {
        // given
        String invalidAuthorization = "invalidToken";
        // when
        mockMvc.perform(get("/api/members")
                .header(AUTHORIZATION_HEADER_NAME, invalidAuthorization))
                // then
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.response").doesNotExist());
    }

    @Test
    public void 회원_목록_조회__조건에_맞는_회원이_없으면_빈_리스트로_응답() throws Exception {
        // given
        String authorization = "Bearer " + jwtFactory.generateToken(haeseong);
        // when
        mockMvc.perform(get("/api/members?size=10&page=10")
                .header(AUTHORIZATION_HEADER_NAME, authorization))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response").isEmpty());
    }

    @Test
    public void 회원_목록_조회__성공() throws Exception {
        // given
        String authorization = "Bearer " + jwtFactory.generateToken(haeseong);
        // when
        mockMvc.perform(get("/api/members")
                .header(AUTHORIZATION_HEADER_NAME, authorization))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response.[0].id").value(haeseong.getId()))
                .andExpect(jsonPath("$.response.[1].id").value(yerin.getId()))
                .andExpect(jsonPath("$.response.[2].id").value(jaeyeon.getId()));
    }

    @Test
    public void 회원_조회__토큰이_유효하지_않으면_401_응답() throws Exception {
        // given
        String invalidAuthorization = "Bearer ";
        // when
        mockMvc.perform(get("/api/members/{memberId}", haeseong.getId())
                .header(AUTHORIZATION_HEADER_NAME, invalidAuthorization))
                // then
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.response").doesNotExist());
    }

    @Test
    public void 회원_조회__주어진_id_의_회원이_없으면_404_응답() throws Exception {
        // given
        String authorization = "Bearer " + jwtFactory.generateToken(haeseong);
        Long notExistMemberId = 12345678901L;
        // when
        mockMvc.perform(get("/api/members/{memberId}", notExistMemberId)
                .header(AUTHORIZATION_HEADER_NAME, authorization))
                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.response").doesNotExist());
    }

    @Test
    public void 회원_조회__다른회원_조회_성공() throws Exception {
        // given
        String authorization = "Bearer " + jwtFactory.generateToken(haeseong);
        // when
        mockMvc.perform(get("/api/members/{memberId}", yerin.getId())
                .header(AUTHORIZATION_HEADER_NAME, authorization))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.response.id").value(yerin.getId()));
    }

    @Test
    public void 자기자신_조회__토큰이_유효하지_않은_경우_401_응답() throws Exception {
        // given
        String invalidAuthorization = "Bearer ";
        // when
        mockMvc.perform(get("/api/members/me")
                .header(AUTHORIZATION_HEADER_NAME, invalidAuthorization))
                // then
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.response").doesNotExist());
    }

    @Test
    public void 자기자신_조회__성공() throws Exception {
        // given
        String authorization = "Bearer " + jwtFactory.generateToken(haeseong);
        // when
        mockMvc.perform(get("/api/members/me")
                .header("authorization", authorization))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.response.id").value(haeseong.getId()));
    }
}
