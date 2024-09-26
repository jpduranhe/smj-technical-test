package com.technical.test.user.domain.exception;

public class UserEmailExistException extends RuntimeException {

    public UserEmailExistException(String message) {
        super(message);
    }
}
