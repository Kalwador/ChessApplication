package com.chess.spring.models.mail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordMail extends Mail {
    private String username;
    private String activationCode;
}