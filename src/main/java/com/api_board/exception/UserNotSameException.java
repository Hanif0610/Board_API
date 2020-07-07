package com.api_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserNotSameException extends RuntimeException {
    public UserNotSameException() {
        super("User Not Same");
    }
}