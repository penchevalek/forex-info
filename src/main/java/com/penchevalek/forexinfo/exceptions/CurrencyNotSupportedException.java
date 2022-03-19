package com.penchevalek.forexinfo.exceptions;

public class CurrencyNotSupportedException extends RuntimeException {

    public CurrencyNotSupportedException(String message) {
        super(message);
    }
}
