package com.penchevalek.forexinfo.exceptions;

public class DuplicatedRequestException extends RuntimeException {

    public DuplicatedRequestException(String message) {
        super(message);
    }
}
