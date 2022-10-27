package explorewithme.ewm.compilation.dto;

import explorewithme.ewm.events.dto.EventShortDto;
import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {

    List<EventShortDto> events;
    long id;
    boolean pinned;
    String title;

}
