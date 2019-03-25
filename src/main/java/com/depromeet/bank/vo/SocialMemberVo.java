package com.depromeet.bank.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class SocialMemberVo {

    @JsonProperty("id")
    Long id;
    @JsonProperty("properties")
    Map<String, String> properties;
    @JsonProperty("kakao_account")
    Map<String, Object> account;


    public Long getUserId() {
        return this.getId();
    }

    public String getUserName() {
        return this.getProperties().get("nickname");
    }

    public String getProfileHref() {
        return this.getProperties().get("profile_image");
    }

}
