package com.rest.library.exceptions;

public class VolumeCantBeDeletedException extends Exception {
    public VolumeCantBeDeletedException() {
        super();
    }

    public VolumeCantBeDeletedException(String message) {
        super(message);
    }
}
