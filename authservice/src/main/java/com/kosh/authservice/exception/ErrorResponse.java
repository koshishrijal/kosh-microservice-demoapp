package com.kosh.authservice.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
    int status;
    String message;
    String description;
}