package com.brsinventory.exception;

public class BRSResourceNotFoundException extends RuntimeException {

    private String message;

    public BRSResourceNotFoundException(String message) {
        this.message = message;
    }

}
