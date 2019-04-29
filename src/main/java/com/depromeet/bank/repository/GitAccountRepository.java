package com.depromeet.bank.repository;

import com.depromeet.bank.domain.data.git.GitAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitAccountRepository extends JpaRepository<GitAccount, Long> {

}
