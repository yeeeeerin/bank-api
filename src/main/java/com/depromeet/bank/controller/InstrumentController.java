package com.depromeet.bank.controller;

import com.depromeet.bank.domain.instrument.Instrument;
import com.depromeet.bank.dto.InstrumentRequest;
import com.depromeet.bank.dto.InstrumentResponse;
import com.depromeet.bank.dto.JoinInstrumentRequest;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.repository.InstrumentRepository;
import com.depromeet.bank.service.InstrumentService;
import com.depromeet.bank.vo.AdjustmentRuleValue;
import com.depromeet.bank.vo.InstrumentValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        List<InstrumentResponse> responses = instrumentService.getInstruments(pageable).stream()
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
    public ResponseDto<InstrumentResponse> createInstrument(@RequestBody @Valid InstrumentRequest instrumentRequest) {
        InstrumentValue instrumentValue = instrumentRequest.toInstrumentValue();
        List<AdjustmentRuleValue> adjustmentRuleValues = instrumentRequest.toAdjustmentRuleValues();

        Instrument instrument = instrumentService.createInstrument(instrumentValue, adjustmentRuleValues);

        InstrumentResponse response = InstrumentResponse.from(instrument);
        return ResponseDto.of(HttpStatus.CREATED, "상품 생성에 성공했습니다.", response);
    }

    @PostMapping("/instruments/{instrumentId:\\d+}/join")
    public ResponseDto<Object> joinInstrument(@RequestAttribute(name = "id") Long memberId,
                                              @PathVariable Long instrumentId,
                                              @RequestBody JoinInstrumentRequest joinInstrumentRequest) {
        Instrument instrument = instrumentService.joinInstrument(memberId, instrumentId, joinInstrumentRequest.getInvestment());
        InstrumentResponse instrumentResponse = InstrumentResponse.from(instrument);
        return ResponseDto.of(HttpStatus.OK, "상품 가입에 성공했습니다.", instrumentResponse);
    }

    @PutMapping("/instruments/{instrumentId:\\d+}")
    public ResponseDto<InstrumentResponse> updateInstrument(@PathVariable Long instrumentId,
                                                            @RequestBody InstrumentRequest instrumentRequest) {
        InstrumentValue instrumentValue = instrumentRequest.toInstrumentValue();
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
