package com.app.springboot.exceptions;

import com.app.springboot.datatransfer.ErrorData;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorData getResult() {
        ErrorData result = new ErrorData();
        result.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        result.setMessage(getMessage());
        result.setStatus(HttpStatus.NOT_FOUND.value());

        return result;
    }
}
