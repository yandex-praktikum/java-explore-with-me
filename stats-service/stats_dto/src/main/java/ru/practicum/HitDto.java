package ru.practicum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitDto {

    Long id;
    @NotNull
    @NotBlank
    String app;
    @NotNull
    @NotBlank
    String uri;
    @NotNull
    @NotBlank
    String ip;
    @NotNull
    @NotBlank
    String timestamp;
}

