package com.depromeet.bank.adapter.mail;

import lombok.Value;

import java.util.Collection;

@Value(staticConstructor = "of")
public class MailValue {
    private final String subject;
    private final String content;
    private final Collection<String> toEmailAddresses;
}
