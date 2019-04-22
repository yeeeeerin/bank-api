package com.depromeet.bank.repository;

import com.depromeet.bank.domain.data.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
