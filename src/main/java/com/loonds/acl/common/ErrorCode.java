package com.loonds.acl.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    BAD_REQUEST(4000, "Bad request"),
    UNAUTHORIZED(4001, "Unauthorized"),
    BAD_CRED(4002, "InvalidCredentials"),
    JWT_TOKEN_EXPIRED(4003, HttpStatus.UNAUTHORIZED, "JWT Token expired.");

    private Integer id;
    private HttpStatus httpStatus;
    private String name;

    ErrorCode(final Integer id, final String name) {
        this.id = id;
        this.name = name;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    ErrorCode(final Integer id, HttpStatus httpStatus, final String name) {
        this.id = id;
        this.name = name;
        this.httpStatus = httpStatus;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
