package com.rest.library.exceptions;

public class VolumeAlreadyReturnedException extends Exception {

    public VolumeAlreadyReturnedException() {
        super();
    }

    public VolumeAlreadyReturnedException(String message) {
        super(message);
    }
}
