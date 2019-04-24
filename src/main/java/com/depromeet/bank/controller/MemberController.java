package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.*;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.service.VisitService;
import com.depromeet.bank.vo.MemberValue;
import com.depromeet.bank.vo.VisitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final VisitService visitService;
    private final VisitService testVisitService;

    public MemberController(MemberService memberService,
                            @Qualifier("visitService") VisitService visitService,
                            @Qualifier("testVisitService") VisitService testVisitService) {
        this.memberService = memberService;
        this.visitService = visitService;
        this.testVisitService = testVisitService;
    }

    @PostMapping("/members/login")
    public ResponseDto<TokenDto> join(@RequestBody TokenDto tokenDto) {

        TokenDto responseTokenDto = new TokenDto();
        responseTokenDto.setToken(memberService.createMember(tokenDto));


        ResponseDto<TokenDto> responseDto = new ResponseDto<>(200, "회원가입에 성공하였습니다.");
        responseDto.setResponse(responseTokenDto);
        return responseDto;

    }

    @GetMapping("/members")
    public ResponseDto<List<MemberResponse>> getMembers(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "20") Integer size,
                                                        @RequestAttribute Long id) {

        log.info(String.valueOf(id));
        Pageable pageable = PageRequest.of(page, size);
        List<MemberResponse> memberResponses = memberService.getMembers(pageable).stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
        return ResponseDto.of(HttpStatus.OK, "회원 조회에 성공했습니다.", memberResponses);
    }

    @GetMapping("/members/{memberId:\\d+}")
    public ResponseDto<MemberResponse> getMember(@PathVariable Long memberId) {

        Member member = memberService.getMember(memberId).orElseThrow(() -> new NotFoundException("회원이 없습니다."));
        MemberResponse memberResponse = MemberResponse.from(member);
        return ResponseDto.of(HttpStatus.OK, "회원 조회에 성공했습니다. ", memberResponse);
    }


    @GetMapping("/members/me")
    public ResponseDto<MemberResponse> getMe(@RequestAttribute Long id) {

        Member member = memberService.getMember(id).orElseThrow(() -> new NotFoundException("회원이 없습니다."));
        MemberResponse memberResponse = MemberResponse.from(member);
        return ResponseDto.of(HttpStatus.OK, "회원 조회에 성공했습니다.", memberResponse);
    }

    @PutMapping("/members/{memberId}")
    public ResponseDto<MemberResponse> updateMember(@RequestAttribute(name = "id") Long memberId,
                                                    @RequestBody MemberUpdateRequest memberUpdateRequest) {
        MemberValue memberValue = MemberValue.from(memberUpdateRequest);
        Member member = memberService.updateMember(memberId, memberValue);
        MemberResponse memberResponse = MemberResponse.from(member);
        return ResponseDto.of(HttpStatus.OK, "회원 갱신에 성공했습니다.", memberResponse);
    }

    /**
     * 멤버가 출석 할 때 호출됩니다.
     * 오늘 처음 출석하는 경우라면, 지급해야할 포인트를 계산하고 출석 포인트 정산 작업을 진행합니다.
     * 오늘 이미 출석 정산 작업이 완료된 경우라면, 204 상태코드를 응답합니다.
     */
    @PostMapping("/members/me/attend")
    public ResponseDto<MemberAttendResponse> attendMember(@RequestAttribute(name = "id") Long memberId) {
        VisitValue visitValue = visitService.attend(memberId);
        MemberAttendResponse memberAttendResponse = MemberAttendResponse.from(visitValue);
        return ResponseDto.of(
                HttpStatus.OK,
                "출석 요청이 성공했습니다.",
                memberAttendResponse
        );
    }

    @PostMapping("/members/me/attend/test")
    public ResponseDto<MemberAttendResponse> attendMemberTest(@RequestAttribute(name = "id") Long memberId) {
        VisitValue visitValue = testVisitService.attend(memberId);
        MemberAttendResponse memberAttendResponse = MemberAttendResponse.from(visitValue);
        return ResponseDto.of(
                HttpStatus.OK,
                "출석 요청이 성공했습니다.",
                memberAttendResponse
        );
    }

}
