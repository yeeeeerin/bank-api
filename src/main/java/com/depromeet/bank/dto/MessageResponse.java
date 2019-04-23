package com.depromeet.bank.dto;

import com.depromeet.bank.domain.data.message.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageResponse {
    @JsonProperty("id")
    private Long messageId;
    private String name;
    private LocalDateTime writtenAt;
    private String content;

    public static MessageResponse from(Message message) {
        return new MessageResponse(
                message.getMessageId(),
                message.getName(),
                message.getWrittenAt(),
                message.getContent()
        );
    }
}
