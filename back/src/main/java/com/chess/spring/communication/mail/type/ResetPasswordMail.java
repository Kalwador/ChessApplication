package com.chess.spring.communication.mail.type;

import com.chess.spring.communication.mail.Mail;
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