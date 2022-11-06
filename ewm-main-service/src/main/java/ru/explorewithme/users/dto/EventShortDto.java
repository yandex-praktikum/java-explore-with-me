package ru.explorewithme.users.dto;

import lombok.Builder;
import lombok.Getter;
import ru.explorewithme.admin.dto.CategoryDto;
import ru.explorewithme.admin.dto.UserShortDto;
import ru.explorewithme.admin.model.Category;
import ru.explorewithme.users.model.Location;

import java.time.LocalDateTime;

@Builder
@Getter
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String eventDate;
    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;

    @Override
    public String toString() {
        return "EventShortDto{" +
                "annotation='" + annotation + '\'' +
                ", category=" + category +
                ", confirmedRequests=" + confirmedRequests +
                ", eventDate='" + eventDate + '\'' +
                ", id=" + id +
                ", initiator=" + initiator +
                ", paid=" + paid +
                ", title='" + title + '\'' +
                ", views=" + views +
                '}';
    }
}
