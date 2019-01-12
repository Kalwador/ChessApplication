package com.chess.spring.communication.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    private String recipient;
    private MailSubject subject;
}