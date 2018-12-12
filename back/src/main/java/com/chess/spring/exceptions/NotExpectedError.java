package com.chess.spring.exceptions;

//500
public class NotExpectedError extends Exception {
    public NotExpectedError(String message) {
        super(message);
    }
}
