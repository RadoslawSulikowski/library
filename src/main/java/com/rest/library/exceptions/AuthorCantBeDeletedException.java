package com.rest.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Author has assigned Book(s) and can't be deleted")
public class AuthorCantBeDeletedException extends Exception {

    public AuthorCantBeDeletedException() {
        super();
    }

    public AuthorCantBeDeletedException(String message) {
        super(message);
    }
}
