package ru.practicum.explore_with_me.ewm_main_service.exceptions;

import ru.practicum.explore_with_me.ewm_main_service.handler.Error;

import java.util.List;

public class NotFoundException extends BaseException {
    public NotFoundException(List<Error> errors, String message, String reason) {
        super(errors, message, reason);
    }
}
