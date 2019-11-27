package com.rest.library.exceptions;

public class VolumeNotFoundException extends Exception {
    public VolumeNotFoundException() {
        super();
    }

    public VolumeNotFoundException(String message) {
        super(message);
    }
}
