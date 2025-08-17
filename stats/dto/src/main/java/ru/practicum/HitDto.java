package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HitDto {
    private int id;
    @NotBlank(message = "app must be not blank")
    private String app;
    @NotBlank(message = "uri must be not blank")
    private String uri;
    @NotBlank(message = "ip must be not blank")
    private String ip;
    @NotBlank(message = "timestamp must be not blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}