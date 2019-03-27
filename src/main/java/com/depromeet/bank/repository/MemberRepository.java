package com.depromeet.bank.repository;

import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);

    Optional<Member> findBySocialId(Long id);

    Optional<List<Account>> findByAccounts(Long id);
}
