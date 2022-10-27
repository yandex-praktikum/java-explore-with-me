package explorewithme.ewm.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import explorewithme.ewm.requests.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ParticipationRequestDto {

    @JsonSerialize
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;
    long event;
    long id;
    long requester;
    Status status;

}
