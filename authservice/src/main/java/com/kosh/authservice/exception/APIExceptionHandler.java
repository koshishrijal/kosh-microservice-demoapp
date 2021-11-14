package com.kosh.authservice.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity handleException(APIException apiException) {
        return ResponseEntity.status(apiException.getStatusCode()).body(ErrorResponse.builder().status(apiException.getStatusCode()).description(apiException.getErrorDescription()).message(apiException.getMessage()).build());
    }


}