package explorewithme.ewm.events.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import explorewithme.ewm.events.model.Location;
import explorewithme.ewm.events.validation.ValidDates;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminUpdateEventRequest {

    long eventId;
    String annotation;
    int category;
    String description;
    @ValidDates(hours = 2, message = "Event updated by admin should be at least 2h in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Location location;
    boolean paid;
    int participantLimit;
    boolean requestModeration;
    String title;
}
