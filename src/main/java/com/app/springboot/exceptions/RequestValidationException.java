package com.app.springboot.exceptions;

public class RequestValidationException extends ApiException {
    public RequestValidationException() {
        super();
    }

    public RequestValidationException(String message) {
        super(message);
    }
}
