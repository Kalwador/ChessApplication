package com.chess.spring.models.tokens;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AccessTokenData {
    private long app_id;
    private String application;
    private long expires_at;
    private boolean is_valid;
    private long issued_at;
    private long user_id;
}
