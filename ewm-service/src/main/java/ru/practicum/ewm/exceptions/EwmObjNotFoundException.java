package ru.practicum.ewm.exceptions;

public class EwmObjNotFoundException extends RuntimeException {
    public EwmObjNotFoundException() {
    }

    public EwmObjNotFoundException(String message) {
        super(message);
    }
}
