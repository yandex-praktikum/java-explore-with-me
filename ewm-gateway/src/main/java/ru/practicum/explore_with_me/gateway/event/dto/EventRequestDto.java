package ru.practicum.explore_with_me.gateway.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.gateway.user.dto.UserRequestDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestDto {
    Long id;
    String title;
    String annotation;
    Object category;
    Boolean paid;
    String eventDate;
    UserRequestDto initiator;
    String description;
    Long participantLimit;
    String state;
    String createdOn;
    Location location;
    Boolean requestModeration;
}
