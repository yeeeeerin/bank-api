package com.depromeet.bank.integration;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.account.JwtFactory;
import com.depromeet.bank.domain.rule.ComparisonType;
import com.depromeet.bank.domain.rule.DataType;
import com.depromeet.bank.domain.rule.NotType;
import com.depromeet.bank.dto.AdjustmentRuleRequest;
import com.depromeet.bank.dto.InstrumentRequest;
import com.depromeet.bank.dto.InstrumentResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.MemberRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class InstrumentTest {
    private static final TypeReference<ResponseDto<List<InstrumentResponse>>> TYPE_REFERENCE_IR_LIST =
            new TypeReference<ResponseDto<List<InstrumentResponse>>>() {
            };
    private static final TypeReference<ResponseDto<InstrumentResponse>> TYPE_REFERENCE_INSTRUMENT_RESPONSE =
            new TypeReference<ResponseDto<InstrumentResponse>>() {
            };
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private static final String INSTRUMENT_NAME_BEFORE = "beforeInstrumentName";
    private static final String INSTRUMENT_DESCRIPTION_BEFORE = "beforeInstrumentDescription";
    private static final LocalDateTime INSTRUMENT_EXPIRED_AT_BEFORE = LocalDateTime.of(2019, 4, 7, 23, 59, 0);
    private static final LocalDateTime INSTRUMENT_TO_BE_SETTLED_AT_BEFORE = LocalDateTime.of(2019, 4, 7, 23, 59, 0);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtFactory jwtFactory;
    private String authorizationHeader;

    @Before
    public void setup() {
        Member haeseong = TestHelper.createMember(1L, "전해성", "http://test.png");
        memberRepository.save(haeseong);
        authorizationHeader = "Bearer " + jwtFactory.generateToken(haeseong);
    }

    @Test
    public void 상품_생성__성공() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        // when
        mockMvc.perform(post("/api/instruments")
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.response.name").value(request.getName()))
                .andExpect(jsonPath("$.response.description").value(request.getDescription()))
                .andExpect(jsonPath("$.response.toBeSettledAt")
                        .value(request.getExpiredAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @Test
    public void 상품_목록_조회__성공() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult oneResult = createInstrument(request);
        MvcResult anotherResult = createInstrument(request);
        MvcResult theOtherResult = createInstrument(request);
        // when
        mockMvc.perform(get("/api/instruments")
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response.[0]").exists())
                .andExpect(jsonPath("$.response.[1]").exists())
                .andExpect(jsonPath("$.response.[2]").exists())
                .andExpect(jsonPath("$.response.[3]").doesNotExist());
    }

    @Test
    public void 상품_목록_조회__조건에_맞는_상품이_없으면_빈리스트_응답() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/instruments")
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader))
                // then 1
                .andExpect(status().isOk())
                .andReturn();
        // then 2
        ResponseDto<List<InstrumentResponse>> responseDto =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_IR_LIST);
        assertThat(responseDto.getResponse()).isEmpty();
    }

    @Test
    public void 상품_조회__해당_상품이_존재하지_않으면_404_응답() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/instruments/{instrumentId}", 1)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader))
                // then 1
                .andExpect(status().isNotFound())
                .andReturn();
        // then 2
        ResponseDto<InstrumentResponse> responseDto =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_IR_LIST);
        assertThat(responseDto.getStatus()).isEqualTo(404);
        assertThat(responseDto.getResponse()).isNull();
    }

    @Test
    public void 상품_조회__성공() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult createResult = createInstrument(request);
        ResponseDto<InstrumentResponse> instrumentResponseDto = objectMapper.readValue(createResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        Long instrumentId = instrumentResponseDto.getResponse().getId();
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/instruments/{instrumentId}", instrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader))
                // then 1
                .andExpect(status().isOk())
                .andReturn();
        // then 2
        ResponseDto<InstrumentResponse> responseDto =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        assertThat(responseDto.getStatus()).isEqualTo(200);
        assertThat(responseDto.getResponse().getId()).isEqualTo(instrumentId);
        assertThat(responseDto.getResponse().getRules().size()).isEqualTo(1);
    }

    @Test
    public void 상품_수정__name_변경_성공() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult createResult = createInstrument(request);
        ResponseDto<InstrumentResponse> instrumentResponseDto = objectMapper.readValue(createResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        Long instrumentId = instrumentResponseDto.getResponse().getId();
        // when
        String requestBody = objectMapper.writeValueAsString(TestHelper.createInstrumentRequest("afterName", null, null, null, null));
        MvcResult mvcResult = mockMvc.perform(put("/api/instruments/{instrumentId}", instrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                // then 1
                .andExpect(status().isOk())
                .andReturn();
        // then 2
        ResponseDto<InstrumentResponse> responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        InstrumentResponse instrumentResponse = responseDto.getResponse();
        assertThat(instrumentResponse.getId()).isEqualTo(instrumentId);
        assertThat(instrumentResponse.getName()).isEqualTo("afterName");
        assertThat(instrumentResponse.getDescription()).isEqualTo(request.getDescription());
        assertThat(instrumentResponse.getExpiredAt()).isEqualTo(request.getExpiredAt());
    }

    @Test
    public void 상품_수정__description_변경_성공() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult createResult = createInstrument(request);
        ResponseDto<InstrumentResponse> instrumentResponseDto = objectMapper.readValue(createResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        Long instrumentId = instrumentResponseDto.getResponse().getId();
        // when
        String requestBody = objectMapper.writeValueAsString(TestHelper.createInstrumentRequest(null, "afterDescription", null, null, null));
        MvcResult mvcResult = mockMvc.perform(put("/api/instruments/{instrumentId}", instrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                // then 1
                .andExpect(status().isOk())
                .andReturn();
        // then 2
        ResponseDto<InstrumentResponse> responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        InstrumentResponse instrumentResponse = responseDto.getResponse();
        assertThat(instrumentResponse.getId()).isEqualTo(instrumentId);
        assertThat(instrumentResponse.getName()).isEqualTo(request.getName());
        assertThat(instrumentResponse.getDescription()).isEqualTo("afterDescription");
        assertThat(instrumentResponse.getExpiredAt()).isEqualTo(request.getExpiredAt());
    }

    @Test
    public void 상품_수정__이전과_동일한_값으로_수정요청하면_기존값_그대로_응답() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult createResult = createInstrument(request);
        ResponseDto<InstrumentResponse> instrumentResponseDto = objectMapper.readValue(createResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        Long instrumentId = instrumentResponseDto.getResponse().getId();
        // when
        String requestBody = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(put("/api/instruments/{instrumentId}", instrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                // then 1
                .andExpect(status().isOk())
                .andReturn();
        // then 2
        ResponseDto<InstrumentResponse> responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        InstrumentResponse instrumentResponse = responseDto.getResponse();
        assertThat(instrumentResponse.getId()).isEqualTo(instrumentId);
        assertThat(instrumentResponse.getName()).isEqualTo(request.getName());
        assertThat(instrumentResponse.getDescription()).isEqualTo(request.getDescription());
        assertThat(instrumentResponse.getExpiredAt()).isEqualTo(request.getExpiredAt());
    }

    @Test
    public void 상품_수정__빈_값으로_수정요청하면_기존값으로_응답() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult createResult = createInstrument(request);
        ResponseDto<InstrumentResponse> instrumentResponseDto = objectMapper.readValue(createResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        Long instrumentId = instrumentResponseDto.getResponse().getId();
        // when
        String requestBody = objectMapper.writeValueAsString(TestHelper.createInstrumentRequest(null, null, null,null, null));
        MvcResult mvcResult = mockMvc.perform(put("/api/instruments/{instrumentId}", instrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                // then 1
                .andExpect(status().isOk())
                .andReturn();
        // then 2
        ResponseDto<InstrumentResponse> responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        InstrumentResponse instrumentResponse = responseDto.getResponse();
        assertThat(instrumentResponse.getId()).isEqualTo(instrumentId);
        assertThat(instrumentResponse.getName()).isEqualTo(request.getName());
        assertThat(instrumentResponse.getDescription()).isEqualTo(request.getDescription());
        assertThat(instrumentResponse.getExpiredAt()).isEqualTo(request.getExpiredAt());
    }

    @Test
    public void 상품_수정__상품이_존재하지_않으면_404_응답() throws Exception {
        // given
        // when
        String requestBody = objectMapper.writeValueAsString(TestHelper.createInstrumentRequest("afterName", "afterDescription", null, null,null));
        mockMvc.perform(put("/api/instruments/{instrumentId}", 1000L)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                // then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.response").doesNotExist());
    }

    @Test
    public void 상품_삭제__상품이_존재하지_않아도_204_응답() throws Exception {
        // given
        Long notExistInstrumentId = 1000L;
        // when
        mockMvc.perform(delete("/api/instruments/{instrumentId}", notExistInstrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader))
                // then
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void 상품_삭제__상품이_존재하면_삭제하고_204_응답() throws Exception {
        // given
        InstrumentRequest request = this.createInstrumentRequest();
        MvcResult createResult = createInstrument(request);
        ResponseDto<InstrumentResponse> instrumentResponseDto = objectMapper.readValue(createResult.getResponse().getContentAsString(), TYPE_REFERENCE_INSTRUMENT_RESPONSE);
        Long instrumentId = instrumentResponseDto.getResponse().getId();
        // when
        mockMvc.perform(delete("/api/instruments/{instrumentId}", instrumentId)
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader))
                // then
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    private MvcResult createInstrument(InstrumentRequest request) throws Exception {
        return mockMvc.perform(post("/api/instruments")
                .header(AUTHORIZATION_HEADER_NAME, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    private InstrumentRequest createInstrumentRequest() {
        return TestHelper.createInstrumentRequest(
                INSTRUMENT_NAME_BEFORE,
                INSTRUMENT_DESCRIPTION_BEFORE,
                INSTRUMENT_EXPIRED_AT_BEFORE,
                INSTRUMENT_TO_BE_SETTLED_AT_BEFORE,
                Collections.singletonList(createAdjustmentRuleRequest())
        );
    }

    private AdjustmentRuleRequest createAdjustmentRuleRequest() {
        return TestHelper.createAdjustmentRuleRequest(
                DataType.NUMBER_OF_ATTENDEE,
                ComparisonType.GREATER_THAN_OR_EQUAL_TO,
                NotType.POSITIVE,
                50L,
                1L,
                LocalDateTime.of(2019, 4, 6, 0, 0, 0),
                LocalDateTime.of(2019, 4, 6, 23, 59, 0),
                0.0
        );
    }
}