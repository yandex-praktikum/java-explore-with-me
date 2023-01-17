package ru.practicum.explore_with_me.ewm_main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.CategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String eventDate;
    Long id;
    UserDto initiator;
    Boolean paid;
    String title;
    Long views;
}
