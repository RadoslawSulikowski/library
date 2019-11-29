package com.rest.library.exceptions;

public class BorrowingAlreadyExistException extends Exception {
    public BorrowingAlreadyExistException() {
        super();
    }

    public BorrowingAlreadyExistException(String message) {
        super(message);
    }
}
