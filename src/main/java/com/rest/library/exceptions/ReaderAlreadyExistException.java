package com.rest.library.exceptions;

public class ReaderAlreadyExistException extends Exception {
    public ReaderAlreadyExistException() {
        super();
    }

    public ReaderAlreadyExistException(String message) {
        super(message);
    }
}
