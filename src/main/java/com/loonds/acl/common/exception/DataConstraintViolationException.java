package com.loonds.acl.common.exception;

public class DataConstraintViolationException extends RuntimeException {
    String message;

    public DataConstraintViolationException(String message) {
        super(message);
        this.message = message;
    }
}
