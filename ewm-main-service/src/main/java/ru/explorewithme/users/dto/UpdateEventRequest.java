package ru.explorewithme.users.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateEventRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    //@Pattern(regexp = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit ;
    @Length(min = 3, max = 120)
    private String title;

    @Override
    public String toString() {
        return "UpdateEventRequest{" +
                "annotation='" + annotation + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventId=" + eventId +
                ", paid=" + paid +
                ", participantLimit=" + participantLimit +
                ", title='" + title + '\'' +
                '}';
    }
}
