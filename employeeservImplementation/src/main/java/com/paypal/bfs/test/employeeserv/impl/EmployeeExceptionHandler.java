package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.model.Error;
import com.paypal.bfs.test.employeeserv.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(value = NOT_FOUND)
    public Error handleDataNotFoundException(DataNotFoundException ex) {
        return populateError(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder("Validation failed. Error count: "+allErrors.size());
        sb.append(":");
        allErrors.forEach(error -> {
            sb.append(error.getCodes()[1]).append(" :: ").append(error.getDefaultMessage()).append(", ");
        });
        return populateError(sb.substring(0, sb.lastIndexOf(",")), BAD_REQUEST);
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
