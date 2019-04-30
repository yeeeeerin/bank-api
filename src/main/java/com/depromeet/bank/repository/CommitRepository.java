package com.depromeet.bank.repository;

import com.depromeet.bank.domain.data.git.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, Long> {

}
