package com.depromeet.bank.controller;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.MemberResponse;
import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.exception.NotFoundException;
import com.depromeet.bank.service.MemberService;
import com.depromeet.bank.utils.JwtFactory;
import lombok.extern.slf4j.Slf4j;
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

<<<<<<< HEAD
    public MemberController(MemberService memberService, JwtFactory jwtFactory) {
=======
    public MemberController(MemberService memberService) {
>>>>>>> eb26b46c8a27929220f4b40b4c670c6a4a3e6b71
        this.memberService = memberService;
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


}
