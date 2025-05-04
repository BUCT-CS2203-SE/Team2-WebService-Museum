package com.example.museum.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** 业务异常，继承 RuntimeException */
public class UserNotExistException extends UsernameNotFoundException {
    public UserNotExistException(String message) {
        super(message);
    }
}
