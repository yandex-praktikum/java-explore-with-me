package ru.practicum.explore_with_me.ewm_main_service.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.BaseException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {

    List<Error> errors;
    String message;
    String reason;
    StatusEnum status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;


    public ApiError(BaseException exception, StatusEnum status) {
        this.errors = exception.getErrors();
        this.message = exception.getMessage();
        this.reason = exception.getReason();
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(MethodArgumentNotValidException exception, String message, String reason, StatusEnum status) {
        this.errors = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new Error(
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(ConstraintViolationException exception, String message, String reason, StatusEnum status) {
        this.errors = exception.getConstraintViolations().stream()
                .map(e -> new Error(StreamSupport.stream(e.getPropertyPath().spliterator(), false)
                        .reduce((first, second) -> second)
                        .orElse(null).toString(), e.getMessage()))
                .collect(Collectors.toList());
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(Throwable exception, String reason, StatusEnum status) {
        this.errors = Arrays.stream(exception.getStackTrace())
                .map(err -> new Error(err.getMethodName(),
                        err.toString()))
                .collect(Collectors.toList());
        this.message = "Произошла непредвиденная ошибка.";
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(DateTimeParseException exception, String message, String reason, StatusEnum status) {
        this.errors = Arrays.stream(exception.getStackTrace())
                .map(err -> new Error(err.getMethodName(),
                        err.toString()))
                .collect(Collectors.toList());
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(IllegalArgumentException exception, String message, String reason, StatusEnum status) {
        this.errors = Arrays.stream(exception.getStackTrace())
                .map(err -> new Error(err.getMethodName(),
                        err.toString()))
                .collect(Collectors.toList());
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
