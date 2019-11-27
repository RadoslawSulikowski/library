package com.rest.library.exceptions;

public class BorrowingNotFoundException extends Exception {
    public BorrowingNotFoundException() {
        super();
    }

    public BorrowingNotFoundException(String message) {
        super(message);
    }
}
