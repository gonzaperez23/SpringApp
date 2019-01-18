package com.app.springboot.exceptions;

import com.app.springboot.datatransfer.ErrorData;
import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

    public ErrorData getResult() {
        ErrorData result = new ErrorData();
        result.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        result.setMessage(getMessage());
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return result;
    }

    public ApiException() {
        super();
    }
    public ApiException(String message) {
        super(message);
    }
}
