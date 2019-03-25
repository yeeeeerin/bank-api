package com.depromeet.bank.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.depromeet.bank.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtFactory {

    @Autowired
    private JwtSettings jwtSettings;

    /*
     * 유저의 권한정보로 토큰을 만듬(claim에는 여러 정보가 올 수 있다.)
     * */
    public String generateToken(Member member) {
        String token;

        token = JWT.create()
                .withIssuer(jwtSettings.getTokenIssuer())
                .withClaim("USERNAME", member.getName())
                .sign(Algorithm.HMAC256(jwtSettings.getTokenSigningKey()));

        log.info("token -- " + token);

        return token;

    }

}
