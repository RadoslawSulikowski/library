package com.rest.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Author with given id already exist")
public class AuthorAlreadyExistException extends Exception {

    public AuthorAlreadyExistException() {
        super();
    }

    public AuthorAlreadyExistException(String message) {
        super(message);
    }
}
