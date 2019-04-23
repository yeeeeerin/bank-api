package com.depromeet.bank.controller.data;

import com.depromeet.bank.dto.AirPollutionResponse;
import com.depromeet.bank.service.AirInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AirPollutionController {
    private final AirInfoService airInfoService;

    @GetMapping("/airpollutions")
    public List<AirPollutionResponse> getAirPollutions(@RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return airInfoService.getAirInfos(pageable)
                .stream()
                .map(AirPollutionResponse::from)
                .collect(Collectors.toList());
    }
}
