package com.depromeet.bank.repository;

import com.depromeet.bank.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findByGuid(UUID guid);

    Page<Transaction> findAllByAccount_Id(Long accountId, Pageable pageable);
}
