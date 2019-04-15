package com.depromeet.bank.domain.account;

import lombok.Getter;


@Getter
public enum KakaoProviders {

    KAKAO("KAKAO", "https://kapi.kakao.com/v2/user/me");

    private String name;
    private String userinfoEndpoint;

    KakaoProviders(String name, String userinfoEndpoint) {
        this.name = name;
        this.userinfoEndpoint = userinfoEndpoint;
    }

}

