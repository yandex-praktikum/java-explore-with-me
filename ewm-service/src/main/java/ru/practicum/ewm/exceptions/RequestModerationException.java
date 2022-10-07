package ru.practicum.ewm.exceptions;

public class RequestModerationException extends RuntimeException {
    public RequestModerationException(String message) {
        super(message);
    }
}
