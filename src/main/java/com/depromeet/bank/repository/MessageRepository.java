package com.depromeet.bank.repository;

import com.depromeet.bank.domain.data.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByWrittenAtGreaterThanAndWrittenAtLessThan(LocalDateTime from, LocalDateTime to);
}
