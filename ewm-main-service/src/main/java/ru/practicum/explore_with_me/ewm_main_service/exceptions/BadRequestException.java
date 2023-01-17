package ru.practicum.explore_with_me.ewm_main_service.exceptions;

import ru.practicum.explore_with_me.ewm_main_service.handler.Error;

import java.util.List;

public class BadRequestException extends BaseException {

    public BadRequestException(String field, String message, String reason) {
        super(field, message, reason);
    }

    public BadRequestException(List<Error> errors, String message, String reason) {
        super(errors, message, reason);
    }
}
