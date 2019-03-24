package com.depromeet.bank.controller;

import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/members")
public class MemberController {

    final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }


    @PostMapping
    public ResponseDto<TokenDto> join(@RequestBody TokenDto tokenDto){

        TokenDto responseTokenDto = new TokenDto();
        responseTokenDto.setToken(memberService.createMember(tokenDto));


        ResponseDto<TokenDto> responseDto = new ResponseDto<>(200,"회원가입에 성공하였습니다.");
        responseDto.setResponse(tokenDto);
        return responseDto;

    }


}
