package com.rest.library.exceptions;

public class VolumeCantBeBorrowedException extends Exception {
    public VolumeCantBeBorrowedException() {
        super();
    }

    public VolumeCantBeBorrowedException(String message) {
        super(message);
    }
}
