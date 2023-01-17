package ru.practicum.explore_with_me.ewm_main_service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handle(RuntimeException ex,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final MethodArgumentNotValidException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, "Ошибка валидации входных данных.", "Неверно заполнены поля.",
                StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final ConstraintViolationException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, "Ошибка валидации входных данных.", "Неверно заполнены поля.",
                StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final DateTimeParseException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, "Ошибка валидации входных данных.", "Неверно заполнены поля.",
                StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, "Ошибка валидации входных данных.", "Неверно заполнены поля.",
                StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, StatusEnum.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(final AccessForbiddenException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, StatusEnum.FORBIDDEN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleArgumentNotValidException(final ArgumentNotValidException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, StatusEnum.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictArgumentsException e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, StatusEnum.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerErrorException(final HttpServerErrorException.InternalServerError e) {
        log.error("Error: {}", e.getMessage());
        return new ApiError(e, e.getMessage(), StatusEnum.INTERNAL_SERVER_ERROR);
    }
}
