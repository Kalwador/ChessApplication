package com.chess.spring.models.mail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountActivationMail extends Mail {
    private String username;
    private String activationCode;
}
