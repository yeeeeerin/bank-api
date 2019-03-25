package com.depromeet.bank.service.impl;

import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.service.SocialFetchService;
import com.depromeet.bank.utils.KakaoProviders;
import com.depromeet.bank.vo.SocialMemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class SocialFetchServiceImpl implements SocialFetchService {

    private static final String HEADER_PREFIX = "Bearer ";

    private final RestTemplate restTemplate;

    @Override
    public SocialMemberVo getSocialUserInfo(TokenDto dto) {
        HttpEntity<String> entity = new HttpEntity<>("parameters", createHeader(dto.getToken()));

        return restTemplate.exchange(KakaoProviders.KAKAO.getUserinfoEndpoint(), HttpMethod.GET, entity, SocialMemberVo.class).getBody();
    }

    private HttpHeaders createHeader(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", createHeaderContent(token));
        return httpHeaders;

    }

    private String createHeaderContent(String token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER_PREFIX);
        stringBuilder.append(token);
        return stringBuilder.toString();
    }
}
