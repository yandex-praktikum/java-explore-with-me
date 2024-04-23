package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder(toBuilder = true)
@Data
@Value
public class EndpointHit {

    Long id;

    @NotNull
    @Size(max = 255)
    String app;

    @NotNull
    @Size(max = 512)
    String uri;

    @NotNull
    @Size(max = 16)
    String ip;

    @NotNull
    String timestamp;
}
