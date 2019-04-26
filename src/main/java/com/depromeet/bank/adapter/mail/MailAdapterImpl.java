package com.depromeet.bank.adapter.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailAdapterImpl implements MailAdapter {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(MailValue mailValue) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailValue.getToEmailAddresses().toArray(new String[0]));
        message.setSubject(mailValue.getSubject());
        message.setText(mailValue.getContent());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            log.error("Failed to send mail. mailValue:{}", mailValue, ex);
        }
    }
}
