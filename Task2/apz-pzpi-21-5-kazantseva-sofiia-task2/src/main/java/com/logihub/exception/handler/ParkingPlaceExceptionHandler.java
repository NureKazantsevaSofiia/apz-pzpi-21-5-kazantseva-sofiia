package com.logihub.exception.handler;

import com.logihub.exception.ParkingPlaceException;
import com.logihub.exception.dto.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ParkingPlaceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ParkingPlaceException.class)
    public ResponseEntity<Object> handleCustomerException(ParkingPlaceException exception,
                                                          WebRequest webRequest) {
        var exceptionBody = new ExceptionResponse(exception.getName(), exception.getMessage());

        return handleExceptionInternal(exception, exceptionBody, new HttpHeaders(),
                exception.getResponseStatus(), webRequest);
    }
}