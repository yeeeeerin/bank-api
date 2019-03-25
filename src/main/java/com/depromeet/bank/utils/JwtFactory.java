package com.depromeet.bank.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.depromeet.bank.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtFactory {

    public static final String HEADER_PREFIX = "Bearer ";

    @Autowired
    private JwtSettings jwtSettings;

    public String generateToken(Member member) {
        String token;

        token = JWT.create()
                .withIssuer(jwtSettings.getTokenIssuer())
                .withClaim("USERNAME", member.getName())
                .withClaim("ID", member.getId())
                .sign(Algorithm.HMAC256(jwtSettings.getTokenSigningKey()));

        log.info("token -- " + token);

        return token;

    }

    public Optional<String> decodeToken(String header) {

        String token = tokenExtractor(header);

        Verification verification = JWT.require(Algorithm.HMAC256(jwtSettings.getTokenSigningKey()));
        JWTVerifier verifier = verification.build();
        DecodedJWT decodedJWT;
        try {
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            return Optional.empty();
        }

        Map<String, Claim> claims = decodedJWT.getClaims();

        return Optional.of(claims.get("ID").asString());
    }

    private String tokenExtractor(String header) {
        if (StringUtils.isEmpty(header)) {
            throw new IllegalArgumentException("Authorization header가 없습니다.");
        }

        if (header.length() < HEADER_PREFIX.length() && header.length() > HEADER_PREFIX.length()) {
            throw new IllegalArgumentException("authorization header size가 옳지 않습니다.");
        }

        if (!header.startsWith(HEADER_PREFIX)) {
            throw new IllegalArgumentException("올바른 header형식이 아닙니다.");
        }

        return header.substring(HEADER_PREFIX.length());
    }

}
