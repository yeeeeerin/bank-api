package com.depromeet.bank.service.impl;

import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.vo.SocialMemberVo;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SocialFetchServiceImplTest {

    private SocialFetchServiceImpl service = new SocialFetchServiceImpl();
    private TokenDto tokenDto;

    @Before
    public void setup() {
        this.tokenDto = new TokenDto("KeA8P6SgKrzQM2rs0XQ7oybYyJndSAR9Qxk7sgopyNoAAAFpr8kSpg");
    }

    @Test
    public void service_social() {

        SocialMemberVo socialMemberVo = service.getSocialUserInfo(this.tokenDto);
        assertThat(socialMemberVo.getUserName(), is("이예린"));
    }

}