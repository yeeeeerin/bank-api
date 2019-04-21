package com.depromeet.bank.service;


import com.depromeet.bank.domain.Member;
import com.depromeet.bank.dto.TokenDto;
import com.depromeet.bank.vo.MemberValue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberService {

    String createMember(TokenDto dto);

    List<Member> getMembers(Pageable pageable);

    Optional<Member> getMember(Long memberId);

    Member updateMember(Long memberId, MemberValue memberValue);

    List<Member> getMembersToBeCorrected();
}
