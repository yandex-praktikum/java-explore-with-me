package ru.practikum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> validationErrorResponse(MethodArgumentNotValidException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.BAD_REQUEST)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.BAD_REQUEST), defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Object> validationErrorResponse(IllegalArgumentException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.BAD_REQUEST)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.BAD_REQUEST), defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> validationErrorResponse(ConstraintViolationException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.BAD_REQUEST)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.BAD_REQUEST), defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> objNotFound(ObjNotFoundException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.NOT_FOUND)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.NOT_FOUND), defaultHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<Object> badRequest(BadRequestException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.BAD_REQUEST)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.BAD_REQUEST), defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public ResponseEntity<Object> objNotFound(EmptyResultDataAccessException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.NOT_FOUND)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.NOT_FOUND), defaultHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseBody
    public ResponseEntity<Object> objExists(PSQLException e) {
        log.info(String.valueOf(errorResponse(e, HttpStatus.CONFLICT)));
        return new ResponseEntity<>(errorResponse(e, HttpStatus.CONFLICT), defaultHeaders(), HttpStatus.CONFLICT);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("X-Error", "true");
        return headers;
    }

    private ErrorResponse errorResponse(Exception e, HttpStatus status) {
        return ErrorResponse.builder()
                //.errors()
                .message(e.getMessage())
                .status(status.getReasonPhrase())
                .reason(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
