package ru.explorewithme.stats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class GetStatRequest {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private Boolean unique;

    public static GetStatRequest of(LocalDateTime start,
                                    LocalDateTime end,
                                    List<String> uris,
                                    Boolean unique) {
        GetStatRequest request = new GetStatRequest();
        request.setStart(start);
        request.setEnd(end);
        request.setUris(uris);
        request.setUnique(unique);
        return request;
    }
}
