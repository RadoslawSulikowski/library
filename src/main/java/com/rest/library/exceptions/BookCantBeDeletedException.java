package com.rest.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "There are volumes of this book in DB")
public class BookCantBeDeletedException extends Exception{

    public BookCantBeDeletedException() {
        super();
    }

    public BookCantBeDeletedException(String message) {
        super(message);
    }
}
