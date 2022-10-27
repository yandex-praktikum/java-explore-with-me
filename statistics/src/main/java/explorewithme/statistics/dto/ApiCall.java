package explorewithme.statistics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCall {

    private long id;
    private String app;
    private String uri;
    private String ip;
    @JsonDeserialize
    private LocalDateTime timestamp;


}
