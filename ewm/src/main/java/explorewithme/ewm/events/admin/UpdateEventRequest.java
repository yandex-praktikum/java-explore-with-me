package explorewithme.ewm.events.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import explorewithme.ewm.events.validation.ValidDates;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateEventRequest {

    String annotation;
    int category;
    String description;
    @ValidDates(hours = 1,  message = "Event updated should be at least 1h in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    long eventId;
    boolean paid;
    int participantLimit;
    String title;

}
