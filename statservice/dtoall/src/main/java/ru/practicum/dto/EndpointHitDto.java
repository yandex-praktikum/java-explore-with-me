package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EndpointHitDto {
    @NotNull
    @Pattern(regexp = "^(?!,*$).+", message = "Названи категории не может быть пустым или содержать один символ")
    private String app;
    @NotNull
    @Pattern(regexp = "^(?!,*$).+", message = "Uri не может быть пустым или содержать один символ")
    private String uri;
    @NotNull
    @Pattern(regexp = "^(?!,*$).+", message = "Ip не может быть пустым или содержать один символ")
    private String ip;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
