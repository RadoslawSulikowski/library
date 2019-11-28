package com.rest.library.exceptions;

public class BookCantBeDeletedException extends Exception {

    public BookCantBeDeletedException() {
        super();
    }

    public BookCantBeDeletedException(String message) {
        super(message);
    }
}
