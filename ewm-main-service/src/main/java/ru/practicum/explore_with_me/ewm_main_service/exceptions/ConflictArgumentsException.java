package ru.practicum.explore_with_me.ewm_main_service.exceptions;

import ru.practicum.explore_with_me.ewm_main_service.handler.Error;

import java.util.List;

public class ConflictArgumentsException extends BaseException {
    public ConflictArgumentsException(List<Error> errors, String message, String reason) {
        super(errors, message, reason);
    }

    public ConflictArgumentsException(String field, String message, String reason) {
        super(field, message, reason);
    }
}
