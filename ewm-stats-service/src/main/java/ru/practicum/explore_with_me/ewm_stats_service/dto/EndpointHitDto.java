package ru.practicum.explore_with_me.ewm_stats_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHitDto {
    Long id;

    String app;

    String uri;

    String ip;

    String timestamp;
}
