package com.depromeet.bank.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtFactoryTest {
    
    @Autowired
    JwtFactory jwtFactory;

    String token;


    @Before
    public void setup() {
        token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ5ZXJpbiIsIlVTRVJOQU1FIjoi7J207JiI66awIn0.splMNykoKxy0td9KpvLklzV5gyxdetxHrXgPgR3167g";
    }


    @Test(expected = RuntimeException.class)
    public void decodeExceptionTest(){
        //given
        String exceptionToken = "gg";

        //when
        jwtFactory.decodeToken(exceptionToken);

        //then
    }


    @Test
    public void decodeTest(){
        //given

        //when
        String resutl = jwtFactory.decodeToken(token);

        //then
        assertThat(resutl,is("이예린"));
    }

}