package com.rest.library.exceptions;

public class AuthorNotFoundException extends Exception {

    public AuthorNotFoundException() {
        super();
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
