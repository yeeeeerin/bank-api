package com.depromeet.bank.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource("classpath:jwtSetting.properties")
public class JwtSettings {

    @Value("${jwt.tokenIssuer}")
    private String tokenIssuer;

    @Value("${jwt.tokenSigningKey}")
    private String tokenSigningKey;

}
