package com.chess.spring.exceptions;

//409
public class DataMissmatchException extends Exception {
    public DataMissmatchException() {
    }

    public DataMissmatchException(String message) {
        super(message);
    }

    public DataMissmatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataMissmatchException(Throwable cause) {
        super(cause);
    }

    public DataMissmatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
