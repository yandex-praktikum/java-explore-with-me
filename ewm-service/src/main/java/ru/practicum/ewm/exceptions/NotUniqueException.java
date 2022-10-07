package ru.practicum.ewm.exceptions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotUniqueException extends RuntimeException {
    public NotUniqueException(String message) {
        super(message);
    }
}
