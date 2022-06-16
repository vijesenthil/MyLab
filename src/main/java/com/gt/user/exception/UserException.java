package com.gt.user.exception;

public class UserException extends Exception {

    public UserException(String message, Exception e) {
        super(message, e);
    }
}
