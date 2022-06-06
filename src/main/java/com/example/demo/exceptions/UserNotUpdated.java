package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserNotUpdated extends IllegalArgumentException {

    public UserNotUpdated() {
        super(String.format("400 - User id do not match exception"));
    }
}
