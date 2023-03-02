package com.example.jissenapi.exception;

public class DealNotFoundException extends RuntimeException {
    public DealNotFoundException(String code) {
        super("Could not find any deals of " + code);
    }
}
