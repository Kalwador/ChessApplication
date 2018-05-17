package com.chess.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class YouJustScrewUpException extends RuntimeException{
    public YouJustScrewUpException(String s) {
    }
}
