package com.loonds.acl.common;

public class ApplicationException extends RuntimeException {
    final ErrorCode errorCode;
    final boolean loggable;

    public ApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.loggable = false;
    }

    public ApplicationException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.loggable = false;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public boolean isLoggable() {
        return loggable;
    }
}
