package ru.practicum.explore_with_me.ewm_main_service.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.ewm_main_service.handler.Error;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {
    List<Error> errors;
    String message;
    String reason;

    public BaseException(Error error, String message, String reason) {
        this(List.of(error), message, reason);
    }

    public BaseException(String field, String message, String reason) {
        this(List.of(new Error(field, message)), message, reason);
    }
}
