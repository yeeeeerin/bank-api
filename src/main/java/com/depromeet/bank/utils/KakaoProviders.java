package com.depromeet.bank.utils;

import com.depromeet.bank.vo.SocialMemberVo;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
public enum  KakaoProviders  {

    KAKAO("KAKAO","https://kapi.kakao.com/v2/user/me");

    private String name;
    private String userinfoEndpoint;

    KakaoProviders(String name,String userinfoEndpoint){
        this.name = name;
        this.userinfoEndpoint = userinfoEndpoint;
    }

}
