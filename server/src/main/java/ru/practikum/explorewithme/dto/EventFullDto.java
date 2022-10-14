package ru.practikum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practikum.explorewithme.model.EventStatus;
import ru.practikum.explorewithme.model.Location;

import java.time.LocalDateTime;

@Builder
@Data
public class EventFullDto {
    private long id;
    private String title;
    private String annotation;
    private String description;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private UserShortDto initiator;
    private Location location;
    private int participantLimit;
    private boolean requestModeration;
    private EventStatus state;
    private boolean paid;
    private int views;
}
