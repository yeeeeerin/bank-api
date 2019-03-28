package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.exception.UnauthorizedException;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.utils.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final JwtFactory jwtFactory;


    /*
     * 계좌 생성
     * todo 어떤 정보를 클라이언트에게 넘겨주면 좋을까?
     * */
    @PostMapping("/accounts/create")
    public ResponseDto createAccount(@RequestHeader(defaultValue = "") String authorization,
                                     @RequestBody AccountDto accountDto) throws IllegalAccessException {

        Long memberId = jwtFactory.getMemberId(authorization)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));

        accountService.createAccount(accountDto, memberId);
        return ResponseDto.of(HttpStatus.OK, "계좌 생성에 성공했습니다.");
    }


    //계좌 조회
    @GetMapping("/accounts")
    public ResponseDto<List<Account>> getAccount(@RequestHeader(defaultValue = "") String authorization,
                                                 @RequestParam(value = "page", defaultValue = "0") int page) throws IllegalAccessException {
        Long memberId = jwtFactory.getMemberId(authorization)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));

        List<Account> accountList = accountService.getAccount(memberId, page);

        return ResponseDto.of(HttpStatus.OK, "계좌 정보 조회에 성공했습니다", accountList);
    }

    //거래내역 조회
    //@GetMapping("/accounts/{accountId:\\d+}")
}
