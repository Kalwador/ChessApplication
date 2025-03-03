package com.chess.spring.communication.mail;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Class provides Mailing System configuration form application properties
 */
@Slf4j
@Getter
@Configuration
public class MailingSystemConfiguration {
    @Value("${email.address}")
    private String email;

    @Value("${email.password}")
    private String password;

    @Value("${email.host}")
    private String host;

    @Value("${mail.smtp.starttls.enable}")
    private String start_tsl;

    @Value("${mail.smtp.ssl.trust}")
    private String ssl_trust;

    @Value("${mail.smtp.port}")
    private String port;

    @Value("${mail.smtp.auth}")
    private String auth;

    public MailingSystemConfiguration() {
        log.info("Mailing system variables inicialized");
    }
}