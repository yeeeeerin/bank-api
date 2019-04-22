package com.depromeet.bank.kakaotalk.parser;

import com.depromeet.bank.kakaotalk.model.ChatMessage;

import java.util.List;

public class WindowKakaoParser implements KakaoParser {

    private List<ChatMessage> chatMessages;

    @Override
    public void parse(String content) {

    }

    @Override
    public List<ChatMessage> getChatMessages() {
        return null;
    }
}
