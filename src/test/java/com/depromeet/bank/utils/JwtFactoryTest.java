package com.depromeet.bank.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.depromeet.bank.config.JwtConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtFactoryTest {

    @Mock
    private JWTVerifier jwtVerifier;
    @Mock
    private JwtConfig.JwtSettings jwtSettings;

    private JwtFactory jwtFactory;
    private String token;

    @Before
    public void setup() {
        token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5ZXJpbiIsIlVTRVJOQU1FIjoi7J207JiI66awIn0.splMNykoKxy0td9KpvLklzV5gyxdetxHrXgPgR3167g";
        jwtSettings = JwtConfig.JwtSettings.of("yerin", "abcdefg");
        jwtFactory = new JwtFactory(jwtVerifier, jwtSettings);
    }


    @Test(expected = RuntimeException.class)
    public void decodeExceptionTest() {
        //given
        String exceptionToken = "gg";

        //when
        jwtFactory.decodeToken(exceptionToken);

        //then
    }


    @Test
    public void decodeTest() {
        //given
        DecodedJWT mockDecodedJWT = mock(DecodedJWT.class);
        when(jwtVerifier.verify(anyString())).thenReturn(mockDecodedJWT);

        Claim mockClaim = mock(Claim.class);
        Map<String, Claim> claimMap = new HashMap<>();
        claimMap.put("ID", mockClaim);
        when(mockDecodedJWT.getClaims()).thenReturn(claimMap);

        when(mockClaim.asString()).thenReturn("이예린");

        //when
        Optional<String> result = jwtFactory.decodeToken(token);

        //then
        assertThat(result.orElse(""), is("이예린"));
    }

}