package ru.practicum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class HitsDto {
    private Long id;

    private String app;

    private String uri;

    private String ip;

    private String timestamp;
}
