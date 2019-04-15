package com.depromeet.bank.repository;

import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.account.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAllByMemberId(Long memberId, Pageable pageable);

    List<Account> findByMemberIdAndAccountType(Long memberId, AccountType accountType);

}
