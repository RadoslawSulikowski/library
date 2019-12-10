package com.rest.library.controller;

import com.rest.library.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AllControllersExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllControllersExceptionHandler.class);

    @ExceptionHandler(AuthorNotFoundException.class)
    public String authorControllerExceptionHandler() {
        LOGGER.error("No such author");
        return "No such author";
    }

    @ExceptionHandler(AuthorCantBeDeletedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Author has assigned Book(s) and can't be deleted")
    public String authorCantBeDeletedExceptionHandler() {
        LOGGER.error("Author has assigned Book(s) and can't be deleted");
        return "Author has assigned Book(s) and can't be deleted";
    }

    @ExceptionHandler(BookNotFoundException.class)
    public String bookNotFoundExceptionHandler() {
        LOGGER.error("No such book");
        return "No suc book";
    }

    @ExceptionHandler(BookCantBeDeletedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "There are volumes of this book in DB")
    public String bookCantBeDeletedExceptionHandler() {
        LOGGER.error("There are volumes of this book in DB");
        return "There are volumes of this book in DB";
    }

    @ExceptionHandler(BorrowingNotFoundException.class)
    public String borrowingNotFoundExceptionHandler() {
        LOGGER.error("No such borrowing");
        return "No such borrowing";
    }

    @ExceptionHandler(ReaderNotFoundException.class)
    public String readerNotFoundExceptionHandler() {
        LOGGER.error("No such reader");
        return "No such reader";
    }

    @ExceptionHandler(ReaderCantBeDeletedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Reader has assigned borrowings and can't be deleted")
    public String readerCantBeDeletedExceptionHandler() {
        LOGGER.error("Reader has assigned borrowings and can't be deleted");
        return "Reader has assigned borrowings and can't be deleted";
    }

    @ExceptionHandler(VolumeNotFoundException.class)
    public String volumeNotFoundExceptionHandler() {
        LOGGER.error("No such volume");
        return "No such volume";
    }

    @ExceptionHandler(VolumeCantBeDeletedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Volume has borrowing(s) added and can't be deleted")
    public String volumeCantBeDeletedExceptionHandler() {
        LOGGER.error("Volume has borrowing(s) added and can't be deleted");
        return "Volume has borrowing(s) added and can't be deleted";
    }

    @ExceptionHandler(VolumeAlreadyReturnedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Volume has no Borrowing without returning date")
    public String volumeAlreadyReturnedExceptionHandler() {
        LOGGER.warn("Volume has no Borrowing without returning date");
        return "Volume has no Borrowing without returning date";
    }

    @ExceptionHandler(VolumeStatusCantBeChangedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Can not change status of borrowed Volume! Return Volume first!")
    public String volumeStatusCantBeChangedExceptionHandler() {
        LOGGER.error("Can not change status of borrowed Volume!");
        return "Can not change status of borrowed Volume!";
    }

    @ExceptionHandler(VolumeCantBeBorrowedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Volume is not to borrow!")
    public String volumeCantBeBorrowedExceptionHandler() {
        LOGGER.error("Volume is not to borrow!");
        return "Volume is not to borrow!";
    }
}
