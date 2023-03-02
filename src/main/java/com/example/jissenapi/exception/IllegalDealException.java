package com.example.jissenapi.exception;

import com.example.jissenapi.model.Position;

public class IllegalDealException extends RuntimeException {
    public IllegalDealException(Position position) {
        super("Could not add Portfolio " + position);
    }
}
