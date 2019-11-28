package com.rest.library.exceptions;

public class AuthorAlreadyExistException extends Exception {

    public AuthorAlreadyExistException() {
        super();
    }

    public AuthorAlreadyExistException(String message) {
        super(message);
    }
}
