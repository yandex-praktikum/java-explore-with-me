package explorewithme.ewm.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@JsonFormat
public class Error {

    public List<String> errors;
    public String message;
    public String reason;
    public HttpStatus status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime timestamp;

    public Error(String message, String reason, HttpStatus status, LocalDateTime timestamp) {
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

}
