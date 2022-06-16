package com.gt.user.exception;

public class DataLoadException extends Exception {

    public DataLoadException(String message, Exception e) {
        super(message, e);
    }
}
