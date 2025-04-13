package enw;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ParamDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private Boolean unique;
}
