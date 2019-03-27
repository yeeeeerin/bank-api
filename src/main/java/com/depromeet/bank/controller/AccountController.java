package com.depromeet.bank.controller;

import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.exception.UnauthorizedException;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.utils.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final JwtFactory jwtFactory;

    /*
     * 계좌 생성
     * todo 어떤 정보를 클라이언트에게 넘겨주면 좋을까?
     * */
    @PostMapping("/members/accounts/create")
    public ResponseDto createAccount(@RequestHeader(defaultValue = "") String authorization,
                                     AccountDto accountDto) throws IllegalAccessException {

        Long memberId = jwtFactory.getMemberId(authorization)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));

        accountService.createAccount(accountDto, memberId);
        return ResponseDto.of(HttpStatus.OK, "계좌 생성에 성공했습니다.");
    }
    //계좌 삭제
}
