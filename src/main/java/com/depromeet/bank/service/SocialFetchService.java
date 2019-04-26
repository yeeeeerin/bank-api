package com.depromeet.bank.service;

import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.vo.SocialMemberVo;

public interface SocialFetchService {

    SocialMemberVo getSocialUserInfo(TokenDto dto);

}
