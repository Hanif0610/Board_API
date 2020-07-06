package com.api_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException() {
        super("User Not Found");
    }
}