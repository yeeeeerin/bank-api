package com.depromeet.bank.domain.data.message;

import com.depromeet.bank.kakaotalk.model.ChatMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue
    private Long messageId;
    private LocalDateTime writtenAt;
    private String name;
    private String content;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Message from(ChatMessage chatMessage) {
        return new Message(
                null,
                chatMessage.getTime().toLocalDateTime(),
                chatMessage.getName(),
                chatMessage.getContent(),
                null,
                null
        );
    }
}
