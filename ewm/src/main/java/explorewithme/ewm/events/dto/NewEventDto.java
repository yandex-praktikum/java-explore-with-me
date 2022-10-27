package explorewithme.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import explorewithme.ewm.events.model.Location;
import explorewithme.ewm.events.validation.ValidDates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @Size(min=20, max = 7000)
    private String description;
    @Size(min=20, max = 2000)
    private String annotation;
    @NotNull
    private int category;
    @ValidDates(hours= 1,  message = "New event should be at least 1h in the future")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    @JsonProperty
    private Location location;
    private long initiator;
    private boolean requestModeration;
    private boolean paid;
    @Size(min=3, max = 120)
    private String title;
    private int participantLimit;


}
