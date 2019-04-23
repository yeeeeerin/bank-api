package com.depromeet.bank.service.impl;

import com.depromeet.bank.domain.data.message.Message;
import com.depromeet.bank.kakaotalk.model.ChatMessage;
import com.depromeet.bank.kakaotalk.parser.AndroidKakaoParser;
import com.depromeet.bank.kakaotalk.parser.KakaoParser;
import com.depromeet.bank.repository.MessageRepository;
import com.depromeet.bank.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<ChatMessage> parse(String content) {
        final KakaoParser androidParser = new AndroidKakaoParser();
        androidParser.parse(content);
        return androidParser.getChatMessages();
    }

    @Override
    @Transactional
    public List<Message> update(String content) {
        messageRepository.deleteAll();

        final KakaoParser androidParser = new AndroidKakaoParser();
        androidParser.parse(content);
        final List<Message> messages = androidParser.getChatMessages()
                .stream()
                .map(Message::from)
                .collect(Collectors.toList());
        return messageRepository.saveAll(messages);
    }

    @Override
    public List<Message> getMessages(Pageable pageable) {
        return messageRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

}
