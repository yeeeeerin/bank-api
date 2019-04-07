package com.depromeet.bank.controller;

import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseDto<String> addTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.createTransaction(transactionRequest);

        return ResponseDto.of(HttpStatus.OK, "transaction done");
    }

}
