package explorewithme.ewm.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {

    String app;
    String uri;
    String ip;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize
    String timestamp;


}
