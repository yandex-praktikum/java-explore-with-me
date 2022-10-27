package explorewithme.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import explorewithme.ewm.users.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    private CategoryDto category;
    private String annotation;
    private int  confirmedRequests;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;

}
