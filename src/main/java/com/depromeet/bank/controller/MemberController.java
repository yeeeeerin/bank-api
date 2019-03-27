package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.MemberResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.exception.UnauthorizedException;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.utils.JwtFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtFactory jwtFactory;

    public MemberController(MemberService memberService,
                            JwtFactory jwtFactory) {
        this.memberService = memberService;
        this.jwtFactory = jwtFactory;
    }

    @PostMapping("/members")
    public ResponseDto<TokenDto> join(@RequestBody TokenDto tokenDto) {

        TokenDto responseTokenDto = new TokenDto();
        responseTokenDto.setToken(memberService.createMember(tokenDto));


        ResponseDto<TokenDto> responseDto = new ResponseDto<>(200, "회원가입에 성공하였습니다.");
        responseDto.setResponse(responseTokenDto);
        return responseDto;

    }

    @GetMapping("/members")
    public ResponseDto<List<MemberResponse>> getMembers(@RequestHeader(defaultValue = "") String authorization,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "20") Integer size) {

        Long memberIdFromToken = jwtFactory.getMemberId(authorization)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));

        Pageable pageable = PageRequest.of(page, size);
        List<MemberResponse> memberResponses = memberService.getMembers(pageable).stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
        return ResponseDto.of(HttpStatus.OK, "회원 조회에 성공했습니다.", memberResponses);
    }


    @GetMapping("/members/me")
    public ResponseDto<MemberResponse> getMe(@RequestHeader(required = false, defaultValue = "") String authorization) {
        Long memberId = jwtFactory.getMemberId(authorization)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));

        Member member = memberService.getMember(memberId).orElseThrow(() -> new NotFoundException("회원이 없습니다."));
        MemberResponse memberResponse = MemberResponse.from(member);
        return ResponseDto.of(HttpStatus.OK, "회원 조회에 성공했습니다.", memberResponse);
    }


}
