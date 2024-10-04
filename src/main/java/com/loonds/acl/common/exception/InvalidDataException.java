package com.loonds.acl.common.exception;

public class InvalidDataException extends RuntimeException {
    String message;

    public InvalidDataException(String message) {
        super(message);
        this.message = message;
    }
}
