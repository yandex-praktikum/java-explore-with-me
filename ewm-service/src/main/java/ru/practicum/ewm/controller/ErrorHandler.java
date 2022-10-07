package ru.practicum.ewm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.exceptions.*;
import ru.practicum.ewm.model.ErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjNotFoundException(final EwmObjNotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                List.of(),
                e.getMessage(),
                "The required object was not found.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleEventStatusException(final EventStatusException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                List.of(),
                e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleInitiatorException(final EventInitiatorException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                List.of(),
                e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleEventDateTimeException(final EventDateTimeException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                List.of(),
                e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNotUniqueValueException(final NotUniqueException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                List.of(),
                e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                List.of(),
                e.getMessage(),
                "Error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
    }

}
