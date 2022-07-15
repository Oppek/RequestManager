package com.oposdevelopment.requestsmanager.exception;

import lombok.Getter;

import static java.text.MessageFormat.format;

public class ApplicationException extends RuntimeException {

    @Getter
    private final String code;

    public ApplicationException(ErrorCode errorCode, Object... params) {
        super(format(errorCode.getMessage(), params));
        this.code = errorCode.name();
    }
}
