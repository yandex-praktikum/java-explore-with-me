package ru.practicum.explore_with_me.ewm_main_service.exceptions;

public class ArgumentNotValidException extends BaseException {
    public ArgumentNotValidException(String field, String message, String reason) {
        super(field, message, reason);
    }
}
