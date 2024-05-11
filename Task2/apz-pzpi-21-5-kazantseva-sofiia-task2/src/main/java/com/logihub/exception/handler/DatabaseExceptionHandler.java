package com.logihub.exception.handler;

import com.logihub.exception.DatabaseException;
import com.logihub.exception.dto.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DatabaseException.class)
    public ResponseEntity<Object> handleCustomerException(DatabaseException exception,
                                                          WebRequest webRequest) {
        var exceptionBody = new ExceptionResponse(exception.getName(), exception.getMessage());

        return handleExceptionInternal(exception, exceptionBody, new HttpHeaders(),
                exception.getResponseStatus(), webRequest);
    }
}
