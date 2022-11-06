package ru.explorewithme.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
public class ApiError {
    private List<StackTraceElement> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private String timestamp;
}
