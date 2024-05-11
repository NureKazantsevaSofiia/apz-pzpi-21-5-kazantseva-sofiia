package com.logihub.exception.dto;

public record ExceptionResponse(
        String errorCode,
        String errorMessage
) {
    public ExceptionResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}