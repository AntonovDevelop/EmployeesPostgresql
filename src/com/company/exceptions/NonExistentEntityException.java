package com.company.exceptions;

public class NonExistentEntityException extends Throwable {

    public NonExistentEntityException(String message) {
        super(message);
    }
}

