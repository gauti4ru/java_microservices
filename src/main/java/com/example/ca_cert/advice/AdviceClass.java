package com.example.ca_cert.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceClass {
    @ExceptionHandler
    public ResponseEntity<String> controllerAdvice(AppException appException) {
        return new ResponseEntity<>(appException.getMessage(), HttpStatus.CONFLICT);
    }
}
