package com.kosh.blogservice.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity handleException(APIException apiException) {
        return ResponseEntity.status(apiException.getStatusCode()).body(ErrorResponse.builder().status(apiException.getStatusCode()).message(apiException.getErrorDescription()).message(apiException.getMessage()).build());
    }


}