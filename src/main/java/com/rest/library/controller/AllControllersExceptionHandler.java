package com.rest.library.controller;

import com.rest.library.exceptions.AuthorNotFoundException;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.ReaderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllControllersExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllControllersExceptionHandler.class);

    @ExceptionHandler(AuthorNotFoundException.class)
    public String authorControllerExceptionHandler() {
        LOGGER.error("No such author");
        return "No such author";
    }

    @ExceptionHandler(BookNotFoundException.class)
    public String bookNotFoundExceptionHandler(){
        LOGGER.error("No such book");
        return "No suc book";
    }

    @ExceptionHandler(BorrowingNotFoundException.class)
    public String borrowingNotFoundExceptionHandler(){
        LOGGER.error("No such borrowing");
        return "No such borrowing";
    }

    @ExceptionHandler(ReaderNotFoundException.class)
    public String readerNotFoundExceptionHandler(){
        LOGGER.error("No such reader");
        return "No such reader";
    }
}
