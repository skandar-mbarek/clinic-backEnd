package com.clinic.userservice.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiBaseException {
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
