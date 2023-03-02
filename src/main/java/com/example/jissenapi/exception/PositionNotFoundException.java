package com.example.jissenapi.exception;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(String code) {
        super("Could not find position of " + code);
    }
}
