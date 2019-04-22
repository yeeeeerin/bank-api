package com.depromeet.bank.kakaotalk.parser;

import com.depromeet.bank.kakaotalk.model.ChatMessage;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IOSKakaoParser implements KakaoParser {

    private static final String REGEX_DATE = "\\d{4}\\. \\d{1,2}\\. \\d{1,2}\\. 오[전후] \\d{1,2}:\\d{1,2}"; // 2018. 8. 25. 오후 3:01
    private static final Pattern PATTERN_MESSAGE_USER = Pattern.compile("(" + REGEX_DATE + "), (.+?) : (.+)");
    private static final Pattern PATTERN_MESSAGE_SYSTEM_INFORMATION = Pattern.compile(REGEX_DATE + ": [^:]*");
    private static final Pattern PATTERN_MESSAGE_SYSTEM_DATE = Pattern.compile("\\d{4}년 \\d{1,2}월 \\d{1,2}일 [월화수목금토일]요일");
    private static final Pattern PATTERN_MESSAGE_FILE_PREFIX = Pattern.compile(".*\\.txt");
    private static final Pattern PATTERN_MESSAGE_FILE_SAVED = Pattern.compile("저장한 날짜.*");
    private List<ChatMessage> chatMessages;

    @Override
    public void parse(String content) {
        List<String> lines = new LinkedList<>(Arrays.asList(content.split("\\r?\\n")));
        List<String> messages = new LinkedList<>();
        // 전처리 : system date 삭제
        // 바로앞의 연속된 빈 문자열로 이루어진 줄도 삭제
        // FIXME: 여러 줄 인 user message 합치기. 일단 버림
        ListIterator<String> iterator = lines.listIterator(lines.size());
        while (iterator.hasPrevious()) {
            String message = iterator.previous();
            if (!isUserMessage(message) || isMetadata(message) || isSystemInformationMessage(message)) {
                    continue;
                }
                if (isSystemDateMessage(message)) {
                    while (iterator.hasPrevious()) {
                        if (!StringUtils.isEmpty(iterator.previous())) {
                            break;
                        }
                    }
                continue;
            }
            messages.add(0, message);
        }

        chatMessages = messages.stream()
                .map(message -> ChatMessage.of(message, PATTERN_MESSAGE_USER))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessage> getChatMessages() {
        if (chatMessages == null) {
            return Collections.emptyList();
        }
        return chatMessages;
    }

    boolean isUserMessage(String content) {
        return PATTERN_MESSAGE_USER.matcher(content).matches();
    }

    boolean isSystemInformationMessage(String content) {
        return PATTERN_MESSAGE_SYSTEM_INFORMATION.matcher(content).matches();
    }

    boolean isSystemDateMessage(String content) {
        return PATTERN_MESSAGE_SYSTEM_DATE.matcher(content).matches();
    }

    boolean isMetadata(String content) {
        return PATTERN_MESSAGE_FILE_SAVED.matcher(content).matches()
                || PATTERN_MESSAGE_FILE_PREFIX.matcher(content).matches();
    }

}
