package com.api_board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotSameException extends RuntimeException {
    public UserNotSameException() {
        super("User Not Same");
    }
}