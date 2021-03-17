package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.model.Error;
import com.paypal.bfs.test.employeeserv.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(value = NOT_FOUND)
    public Error handleDataNotFoundException(DataNotFoundException ex) {
        return populateError(ex.getMessage(), NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return populateError(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public Error handleOtherException(RuntimeException ex) {
        return populateError(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    private Error populateError(String message, HttpStatus status) {
        Error error = new Error();
        error.setMessage(message);
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setTimestamp(LocalDateTime.now());
        return error;
    }
}
