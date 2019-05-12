package com.depromeet.bank.controller;

import com.depromeet.bank.dto.RankResponse;
import com.depromeet.bank.dto.RankType;
import com.depromeet.bank.exception.BadRequestException;
import com.depromeet.bank.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    /**
     * 자산을 기준으로 정렬한 결과를 돌려줍니다.
     *
     * @param type 정렬 기준
     * @return 순위 화면에 필요한 정보 리스트
     */
    @PostMapping("/members/rank")
    public List<RankResponse> getUsers(@RequestParam RankType type) {
        if (type == RankType.UNKNOWN) {
            throw new BadRequestException("'type' 이 올바르지 않습니다. {" + RankType.VALID_TYPES + "} 중에서 요청해주세요.");
        }
        return rankService.getRankedMembers()
                .stream()
                .map(RankResponse::from)
                .collect(Collectors.toList());
    }
}
