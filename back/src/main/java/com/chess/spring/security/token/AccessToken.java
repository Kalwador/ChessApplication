package com.chess.spring.security.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AccessToken {
    private String access_token;
    private long expires_in;
}
