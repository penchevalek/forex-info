package com.penchevalek.forexinfo.controller;

import com.penchevalek.forexinfo.exceptions.CurrencyNotSupportedException;
import com.penchevalek.forexinfo.exceptions.DuplicatedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedRequestException.class)
    public ResponseEntity handleDuplicatedRequestException(DuplicatedRequestException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CurrencyNotSupportedException.class)
    public ResponseEntity handleCurrencyNotSupportedException(CurrencyNotSupportedException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
