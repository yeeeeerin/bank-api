package com.depromeet.bank.integration;

import com.depromeet.bank.adapter.mail.MailAdapter;
import com.depromeet.bank.adapter.mail.MailValue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Ignore
@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MailAdapterTest {

    @Autowired
    private MailAdapter mailAdapter;

    @Test
    public void 메일_발송_테스트() {
        final String subject = "메일 발송 테스트";
        final String content = "메일 발송이 잘 되는지 테스트하는 메일입니다 :)\n" + "발송시각 : " + LocalDateTime.now();
        final List<String> toMailAddresses = Collections.singletonList("depromeet.billionaire@gmail.com");
        mailAdapter.send(MailValue.of(subject, content, toMailAddresses));
    }
}
