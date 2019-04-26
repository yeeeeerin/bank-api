package com.depromeet.bank.controller.data;

import com.depromeet.bank.dto.GraphResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GraphController {
    private final GraphService graphService;

    @GetMapping("/graphs/session")
    public ResponseDto<List<GraphResponse>> getDepromeetSessionData() {
        return ResponseDto.of(
                HttpStatus.OK,
                "디프만 세션 그래프 데이터 조회에 성공했습니다.",
                graphService.getDepromeetSessionData()
                        .stream()
                        .map(GraphResponse::from)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/graphs/airpollution")
    public ResponseDto<List<GraphResponse>> getAirPollutionData() {
        return ResponseDto.of(
                HttpStatus.OK,
                "미세먼지 그래프 데이터 조회에 성공했습니다.",
                graphService.getAirPollutionData()
                        .stream()
                        .map(GraphResponse::from)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/graphs/kakaotalk")
    public ResponseDto<List<GraphResponse>> getKakaoTalkData() {
        return ResponseDto.of(
                HttpStatus.OK,
                "카카오톡 그래프 데이터 조회에 성공했습니다.",
                graphService.getKakaotalkData()
                        .stream()
                        .map(GraphResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
