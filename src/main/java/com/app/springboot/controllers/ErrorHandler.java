package com.app.springboot.controllers;

import com.app.springboot.datatransfer.ErrorData;
import com.app.springboot.exceptions.ApiException;
import com.app.springboot.exceptions.NotFoundException;
import com.app.springboot.exceptions.RequestValidationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({RequestValidationException.class})
    public HttpEntity<ErrorData> handleValidationError(ApiException ex) {
        ErrorData result = ex.getResult();

        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatus());

        return new ResponseEntity<>(result, httpStatus);
    }

    @ExceptionHandler({NotFoundException.class})
    public HttpEntity<ErrorData> handleNotFoundError(NotFoundException ex) {
        ErrorData result = ex.getResult();

        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatus());

        return new ResponseEntity<>(result, httpStatus);
    }

    @ExceptionHandler({ApiException.class})
    public HttpEntity<ErrorData> handleInternalError(ApiException ex) {
        ErrorData result = ex.getResult();

        HttpStatus httpStatus = HttpStatus.valueOf(result.getStatus());

        return new ResponseEntity<>(result, httpStatus);
    }
}
