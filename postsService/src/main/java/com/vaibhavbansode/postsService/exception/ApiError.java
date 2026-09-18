package com.vaibhavbansode.postsService.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    ApiError(){
        timestamp = LocalDateTime.now();
    }
    public ApiError(String message, HttpStatus status) {
        this();
        this.message = message;
        this.status = status;
    }
}
