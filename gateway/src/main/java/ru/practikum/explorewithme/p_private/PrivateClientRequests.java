package ru.practikum.explorewithme.p_private;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practikum.explorewithme.client.BaseClient;

import java.util.Map;

@Service
public class PrivateClientRequests extends BaseClient {

    @Autowired
    public PrivateClientRequests(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getRequests(long userId) {
        return get("users/" + userId + "/requests");
    }

    public ResponseEntity<Object> createRequest(long userId, long eventId) {
        Map<String, Object> parameters = Map.of(
                "eventId", eventId
        );
        return post("users/" + userId + "/requests?eventId={eventId}", parameters);
    }

    public ResponseEntity<Object> cancelRequest(long userId, long requestId) {
        return patch("users/" + userId + "/requests/" + requestId + "/cancel");
    }
}
