package com.depromeet.bank.controller.data;

import com.depromeet.bank.dto.GitAccountRequest;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.service.GitAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GitAccountController {

    private final GitAccountService gitAccountService;

    @PostMapping("/gitAccount")
    public ResponseDto saveAccount(@RequestBody GitAccountRequest gitAccountRequest) {

        gitAccountService.saveAccount(gitAccountRequest);
        return ResponseDto.of(
                HttpStatus.OK,
                "등록되었습니다."
        );
    }


}
