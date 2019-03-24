package com.depromeet.bank.repository;

import com.depromeet.bank.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {



}
