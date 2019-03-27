package com.depromeet.bank.controller;

import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    /*
     * 계좌 생성
     * todo 어떤 정보를 클라이언트에게 넘겨주면 좋을까?
     * */
    @PostMapping("/members/{memberId:\\d+}/accounts/create")
    public ResponseDto createAccount(@RequestHeader(defaultValue = "") String authentication,
                                     @PathVariable Long memberId,
                                     AccountDto accountDto) {

        //todo id값이 있는지 없는지 확인

        accountDto.setMemberId(memberId);

        return ResponseDto.of(HttpStatus.OK, "계좌 생성에 성공했습니다.");
    }
    //계좌 삭제
}
