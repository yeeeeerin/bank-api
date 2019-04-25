package com.depromeet.bank.controller;

import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.dto.TransactionResponse;
import com.depromeet.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/accounts/{accountId:\\d+}/transfer")
    public ResponseDto addTransaction(@PathVariable Long accountId,
                                      @RequestAttribute Long id,
                                      @RequestBody TransactionRequest transactionRequest) {

        transactionService.createTransaction(id, transactionRequest);

        return ResponseDto.of(HttpStatus.OK, "transaction done");
    }

    @GetMapping("/accounts/{accountId:\\d+}/transactions")
    public ResponseDto<List<TransactionResponse>> getTransaction(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "20") Integer size,
                                                                 @PathVariable Long accountId,
                                                                 @RequestAttribute Long id) {

        Pageable pageable = PageRequest.of(page, size);
        List<TransactionResponse> transactionResponses = transactionService.getTransaction(id, accountId, pageable)
                .stream()
                .map(TransactionResponse::from)
                .collect(Collectors.toList());

        return ResponseDto.of(HttpStatus.OK, "이체 정보 조회에 성공했습니다", transactionResponses);
    }


    @PostMapping("/accounts/{accountId:\\d+}/transactions/cancel")
    public void deleteTransaction(@RequestAttribute Long id,
                                  @RequestBody UUID guid) {
        transactionService.deleteTransaction(id, guid);
    }


}
