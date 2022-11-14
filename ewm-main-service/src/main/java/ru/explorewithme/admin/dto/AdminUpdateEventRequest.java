package ru.explorewithme.admin.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.explorewithme.users.model.Location;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class AdminUpdateEventRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    private LocalDateTime eventDate;
    @NotNull
    private Location location;
    @Builder.Default
    private Boolean paid = false;
    @Builder.Default
    private Integer participantLimit = 0;
    @Builder.Default
    private Boolean requestModeration = true;
    @Length(min = 3, max = 120)
    private String title;

    @Override
    public String toString() {
        return "AdminUpdateEventRequest{" +
                "annotation='" + annotation + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", location=" + location +
                ", paid=" + paid +
                ", participantLimit=" + participantLimit +
                ", requestModeration=" + requestModeration +
                ", title='" + title + '\'' +
                '}';
    }
}
