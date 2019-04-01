package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Instrument;
import com.depromeet.bank.dto.InstrumentRequest;
import com.depromeet.bank.dto.InstrumentResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.service.InstrumentService;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InstrumentController {

    private final InstrumentService instrumentService;

    @GetMapping("/instruments")
    public ResponseDto<List<InstrumentResponse>> getInstruments(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<InstrumentResponse> responses =  instrumentService.getInstruments(pageable).stream()
                .map(InstrumentResponse::from)
                .collect(Collectors.toList());
        return ResponseDto.of(HttpStatus.OK, "상품 목록 조회에 성공했습니다.", responses);
    }

    @GetMapping("/instruments/{instrumentId:\\d+}")
    public ResponseDto<InstrumentResponse> getInstrument(@PathVariable Long instrumentId) {
        InstrumentResponse response = instrumentService.getInstrument(instrumentId)
                .map(InstrumentResponse::from)
                .orElseThrow(() -> new NotFoundException("상품이 없습니다."));
        return ResponseDto.of(HttpStatus.OK, "상품 조회에 성공했습니다.", response);
    }

    @PostMapping("/instruments")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<InstrumentResponse> createInstrument(@RequestBody InstrumentRequest instrumentRequest) {
        InstrumentValue instrumentValue = InstrumentValue.from(instrumentRequest);
        Instrument instrument = instrumentService.createInstrument(instrumentValue);
        InstrumentResponse response = InstrumentResponse.from(instrument);
        return ResponseDto.of(HttpStatus.CREATED, "상품 생성에 성공했습니다.", response);
    }

    @PutMapping("/instruments/{instrumentId:\\d+}")
    public ResponseDto<InstrumentResponse> updateInstrument(@PathVariable Long instrumentId,
                                                            @RequestBody InstrumentRequest instrumentRequest) {
        InstrumentValue instrumentValue = InstrumentValue.from(instrumentRequest);
        Instrument instrument = instrumentService.updateInstrument(instrumentId, instrumentValue);
        InstrumentResponse response = InstrumentResponse.from(instrument);
        return ResponseDto.of(HttpStatus.OK, "상품 수정에 성공했습니다.", response);
    }

    @DeleteMapping("/instruments/{instrumentId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstrument(@PathVariable Long instrumentId) {
        instrumentService.deleteInstrument(instrumentId);
    }
}
