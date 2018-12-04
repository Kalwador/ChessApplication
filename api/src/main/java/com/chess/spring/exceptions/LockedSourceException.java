package com.chess.spring.exceptions;

//423
public class LockedSourceException extends Exception {

    public LockedSourceException() {
    }

    public LockedSourceException(String message) {
        super(message);
    }

    public LockedSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockedSourceException(Throwable cause) {
        super(cause);
    }

    public LockedSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
