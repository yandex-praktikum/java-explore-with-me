import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.HitDto;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsClient {
    protected final RestTemplate rest;

    public ResponseEntity<Object> postHit(HitDto hitDto) {
        return makeAndSendRequest(HttpMethod.POST, "/hit", null, hitDto);
    }

    public ResponseEntity<Object> getStats(String start,
                                           String end,
                                           List<String> uris,
                                           Boolean unique) {
        Map<String, Object> parameters = Map.of("start", start, "end", end, "uris", uris, "unique", unique);
        return makeAndSendRequest(HttpMethod.GET, "/stats", parameters, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        ResponseEntity<Object> responseEntity;
        try {
            if (parameters != null) {
                responseEntity = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                responseEntity = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepare(responseEntity);
    }

    private static ResponseEntity<Object> prepare(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}