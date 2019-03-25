package com.depromeet.bank.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class SocialMemberVo {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("properties")
    private Map<String, String> properties;
    @JsonProperty("kakao_account")
    private Map<String, Object> account;


    public Long getUserId() {
        return this.id;
    }

    public String getUserName() {
        return this.properties.get("nickname");
    }

    public String getProfileHref() {
        return this.properties.get("profile_image");
    }

}
