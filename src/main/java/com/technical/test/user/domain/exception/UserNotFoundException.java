package com.technical.test.user.domain.exception;

public class UserNotFoundException extends  RuntimeException{

        public UserNotFoundException(String message) {
            super(message);
        }
}
