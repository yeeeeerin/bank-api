package com.depromeet.bank.kakaotalk.parser;

import com.depromeet.bank.kakaotalk.model.ChatMessage;

import java.util.List;

public interface KakaoParser {
    /**
     * 시간, 사람, 내용
     * @param content
     */
    void parse(String content);

    List<ChatMessage> getChatMessages();
}
