package com.example.ca_cert.advice;

import com.example.ca_cert.data.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

@ControllerAdvice
public class AdviceClass {
@ExceptionHandler
    public ResponseEntity<String> ControllerAdvice(AppException appException)
    {
        return new ResponseEntity<>(appException.getMessage(), HttpStatus.CONFLICT);
    }
}
