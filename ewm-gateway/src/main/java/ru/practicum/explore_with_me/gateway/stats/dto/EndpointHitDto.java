package ru.practicum.explore_with_me.gateway.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHitDto {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitDto(String uri, String ip) {
        this.app = "ewm-main-service";
        this.timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        this.uri = uri;
        this.ip = ip;
    }

    Long id;

    String app;

    String uri;

    String ip;

    String timestamp;
}
