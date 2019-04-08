package com.depromeet.bank.repository;

import com.depromeet.bank.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAllByMember_Id(Long memberId, Pageable pageable);

}
