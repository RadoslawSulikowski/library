package com.rest.library.exceptions;

public class VolumeStatusCantBeChangedException extends Exception {
    public VolumeStatusCantBeChangedException() {
    }

    public VolumeStatusCantBeChangedException(String message) {
        super(message);
    }
}
