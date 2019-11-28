package com.rest.library.exceptions;

public class AuthorCantBeDeletedException extends Exception {

    public AuthorCantBeDeletedException() {
        super();
    }

    public AuthorCantBeDeletedException(String message) {
        super(message);
    }
}
