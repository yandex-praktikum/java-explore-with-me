package ru.practicum.statsserver.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class EndpointDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
