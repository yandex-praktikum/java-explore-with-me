package ru.practicum.ewm.dto.newDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.ewm.model.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotNull
    @Length(min = 3, max = 120)
    private String title;
    @NotNull
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotNull
    @Length(min = 20, max = 7000)
    private String description;
    @Positive
    private int category;
    @NotNull
    private Location location;
    @Value("0")
    private Integer participantLimit;
    @Value("false")
    private Boolean paid;
    @Value("true")
    private Boolean requestModeration;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn = LocalDateTime.now();
}
