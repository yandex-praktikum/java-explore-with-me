package ru.practikum.explorewithme.stat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
