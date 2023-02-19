package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticEventDto {
    @JsonProperty("id")
    long id;
    @NotNull
    @NotBlank
    @JsonProperty("app")
    String application;
    @NotNull
    @NotBlank
    @JsonProperty("uri")
    String uri;
    @NotNull
    @NotBlank
    @JsonProperty("ip")
    String ip;
    @NotNull
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventTime;
}
