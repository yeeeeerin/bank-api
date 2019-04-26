package com.depromeet.bank.scheduler;

import com.depromeet.bank.adapter.mail.MailAdapter;
import com.depromeet.bank.adapter.mail.MailValue;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberInspectionScheduler {

    private final MemberService memberService;
    private final MailAdapter mailAdapter;

    @Scheduled(cron = "0 0 0 * * *")
    public void inspect() {
        Collection<Member> members = memberService.getMembersToBeCorrected();

        MailValue mailValue = MailValue.of(
                "[디프가즈아] 회원 정보 조회 알림",
                "실명이 아니거나, 기수가 입력되지 않은 회원들의 목록입니다.\n\n" +
                        members.stream()
                                .map(Member::toString)
                                .collect(Collectors.joining("\n")),
                Collections.singletonList("depromeet.billionaire@gmail.com")
        );
        mailAdapter.send(mailValue);
    }
}
