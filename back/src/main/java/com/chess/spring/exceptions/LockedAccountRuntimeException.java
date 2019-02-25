package com.chess.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class LockedAccountRuntimeException extends RuntimeException {

    public LockedAccountRuntimeException() {
    }

    public LockedAccountRuntimeException(String message) {
        super(message);
    }

    public LockedAccountRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockedAccountRuntimeException(Throwable cause) {
        super(cause);
    }

    public LockedAccountRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
