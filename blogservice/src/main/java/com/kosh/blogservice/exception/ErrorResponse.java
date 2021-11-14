package com.kosh.blogservice.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
    int status;
    String message;
    String description;
}