package explorewithme.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import explorewithme.ewm.events.State;
import explorewithme.ewm.events.model.Location;
import explorewithme.ewm.users.dto.UserShortDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventFullDto {


    String annotation;
    CategoryDto category;
    int confirmedRequests;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;
    String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    long id;
    UserShortDto initiator;
    Location location;
    boolean paid;
    int participantLimit;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;
    boolean requestModeration;
    State state;
    String title;
    int views;

}
