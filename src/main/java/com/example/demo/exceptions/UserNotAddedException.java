package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserNotAddedException extends IllegalArgumentException {

    public UserNotAddedException() {
        super(String.format("422 - User NOT added/created"));
    }
}
