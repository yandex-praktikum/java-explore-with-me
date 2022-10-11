package ru.practicum.ewmservice.exceptions;

public class RequestModerationException extends RuntimeException {
    public RequestModerationException(String message) {
        super(message);
    }
}
