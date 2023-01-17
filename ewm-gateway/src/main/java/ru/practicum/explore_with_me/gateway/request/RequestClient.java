package ru.practicum.explore_with_me.gateway.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore_with_me.gateway.client.BaseClient;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;

import java.util.Map;


@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    RequestClient(@Value("${server.ewm.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addRequest(Long eventId, Long userId, RoleEnum role) {
        if (eventId != null) {
            return post("?eventId={eventId}", userId, role, Map.of("eventId", eventId));
        } else {
            return post("", userId, role);
        }
    }

    public ResponseEntity<Object> findRequests(Long eventId, Long userId, RoleEnum role) {
        return get("/event/" + eventId, userId, role);
    }

    public ResponseEntity<Object> rejectRequest(Long eventId, Long reqId, Long userId, RoleEnum role) {
        return patch("/" + reqId + "/event/" + eventId + "/reject", userId, role);
    }

    public ResponseEntity<Object> confirmRequest(Long eventId, Long reqId, Long userId, RoleEnum role) {
        return patch("/" + reqId + "/event/" + eventId + "/confirm", userId, role);
    }

    public ResponseEntity<Object> findRequests(Long userId, RoleEnum role) {
        return get("", userId, role);
    }

    public ResponseEntity<Object> cancelRequest(Long reqId, Long userId, RoleEnum role) {
        return patch("/" + reqId + "/cancel", userId, role);
    }
}
