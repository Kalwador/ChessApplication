package com.chess.spring.exceptions;

public class NotExpectedError extends Exception {
    public NotExpectedError(String message) {
        super(message);
    }
}
