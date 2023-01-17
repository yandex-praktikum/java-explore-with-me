package ru.practicum.explore_with_me.gateway.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventRequest {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Long eventId;
    Boolean paid;
    Integer participantLimit;
    String title;
}
