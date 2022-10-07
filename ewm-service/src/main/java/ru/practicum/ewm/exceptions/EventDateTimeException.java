package ru.practicum.ewm.exceptions;

public class EventDateTimeException extends RuntimeException {
    public EventDateTimeException() {
    }

    public EventDateTimeException(String message) {
        super(message);
    }
}
