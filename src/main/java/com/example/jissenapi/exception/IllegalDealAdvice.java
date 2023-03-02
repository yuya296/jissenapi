package com.example.jissenapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IllegalDealAdvice {
    @ResponseBody
    @ExceptionHandler(IllegalDealException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //TODO: このリクエストは妥当か？
    String illegalDealHandler(IllegalDealException ex) {
        return ex.getMessage();
    }
}
