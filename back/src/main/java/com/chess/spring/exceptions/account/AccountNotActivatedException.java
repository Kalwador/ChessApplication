package com.chess.spring.exceptions.account;

import org.springframework.security.core.AuthenticationException;

public class AccountNotActivatedException extends AuthenticationException {

    public AccountNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AccountNotActivatedException(String msg) {
        super(msg);
    }
}
