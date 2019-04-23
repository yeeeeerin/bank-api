package com.depromeet.bank.service;

import com.depromeet.bank.domain.data.message.Message;
import com.depromeet.bank.kakaotalk.model.ChatMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    List<ChatMessage> parse(String content);

    List<Message> update(String content);

    List<Message> getMessages(Pageable pageable);
}
