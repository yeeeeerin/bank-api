package com.depromeet.bank.kakaotalk.model;

import lombok.Getter;
import lombok.ToString;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@Getter
public final class ChatMessage {
    private static final Pattern PATTERN_DATE_GROUP =
            Pattern.compile("(\\d{4})\\. (\\d{1,2})\\. (\\d{1,2})\\. (오[전후]) (\\d{1,2}):(\\d{1,2})");

    private ZonedDateTime time;
    private String name;
    private String content;

    private ChatMessage() {
    }

    public static ChatMessage of(String data, Pattern messagePattern) {
        Matcher matcher = messagePattern.matcher(data);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("data is not matches message format: '" + data + "'");
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.name = matcher.group(2).replaceAll("`|•", "");
        chatMessage.time = convertToZonedDateTime(matcher.group(1), PATTERN_DATE_GROUP);
        chatMessage.content = matcher.group(3);
        return chatMessage;
    }

    public static ChatMessage of(String data, Pattern messagePattern, Pattern datePattern) {
        Matcher matcher = messagePattern.matcher(data);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("data is not matches message format: '" + data + "'");
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.name = matcher.group(2);
        chatMessage.time = convertToZonedDateTime(matcher.group(1), datePattern);
        chatMessage.content = matcher.group(3);
        return chatMessage;
    }

    private static ZonedDateTime convertToZonedDateTime(String date, Pattern datePattern) {
        Matcher matcher = datePattern.matcher(date);
        if (!matcher.matches()) {
            return null;
        }
        return ZonedDateTime.of(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                (Integer.parseInt(matcher.group(5)) + ("오후".equals(matcher.group(4)) ? 12 : 0)) % 24,
                Integer.parseInt(matcher.group(6)),
                0,
                0,
                ZoneId.systemDefault());
    }

    public boolean isWrittenAt(int year) {
        if (time == null) {
            return false;
        }
        return time.getYear() == year;
    }
}
