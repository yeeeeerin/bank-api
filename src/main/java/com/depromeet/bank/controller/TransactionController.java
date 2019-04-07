package com.depromeet.bank.controller;

import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;
import com.depromeet.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseDto<String> addTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.createTransaction(transactionRequest);

        return ResponseDto.of(HttpStatus.OK, "transaction done");
    }

    @GetMapping("/transactions")
    public ResponseDto<List<TransactionResponse>> getTransaction(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestAttribute Long accountId) {

        List<TransactionResponse> transactionList = transactionService.getTransaction(accountId, page);

        return ResponseDto.of(HttpStatus.OK, "이체 정보 조회에 성공했습니다", transactionList);
    }


    @PostMapping("/transactions/cancel")
    public void deleteTransaction(UUID guid) {
        transactionService.deleteTransaction(guid);
    }


}
