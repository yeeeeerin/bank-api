package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.dto.AccountDto;
import com.depromeet.bank.dto.AccountResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.service.AccountService;
import com.depromeet.bank.utils.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final JwtFactory jwtFactory;


    //계좌 생성
    @PostMapping("/accounts")
    public ResponseDto createAccount(@RequestBody AccountDto accountDto,
                                     @RequestAttribute Long id) {

        Account account = accountService.createAccount(accountDto, id)
                .orElseThrow(() -> new NotFoundException("계좌생성에 실패하였습니다."));

        return ResponseDto.of(HttpStatus.CREATED, "계좌 생성에 성공했습니다.", AccountResponse.from(account));
    }


    //계좌 조회
    @GetMapping("/accounts")
    public ResponseDto<List<AccountResponse>> getAccount(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestAttribute Long id) {

        List<AccountResponse> accountList = accountService.getAccount(id, page).stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());

        return ResponseDto.of(HttpStatus.OK, "계좌 정보 조회에 성공했습니다", accountList);
    }

    //거래내역 조회
    //@GetMapping("/accounts/{accountId:\\d+}")
}
