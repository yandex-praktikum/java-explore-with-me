package ru.practicum.explore_with_me.gateway.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore_with_me.gateway.client.BaseClient;
import ru.practicum.explore_with_me.gateway.client.PathBuilder;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;
import ru.practicum.explore_with_me.gateway.event.dto.EventRequestDto;
import ru.practicum.explore_with_me.gateway.event.dto.NewEventDto;
import ru.practicum.explore_with_me.gateway.event.dto.UpdateEventRequest;

@Service
public class EventClient extends BaseClient {
    private static final String API_PREFIX = "/events";

    @Autowired
    EventClient(@Value("${server.ewm.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> createEvent(Long userId, NewEventDto requestDto, RoleEnum role) {
        return post("", userId, role, requestDto);
    }

    public ResponseEntity<Object> editEvent(Long userId, UpdateEventRequest requestDto, RoleEnum role) {
        return patch("", userId, role, requestDto);
    }

    public ResponseEntity<Object> getEvent(Long eventId, RoleEnum role) {
        return get("/" + eventId, role);
    }

    public ResponseEntity<Object> getEvent(Long eventId, Long userId, RoleEnum role) {
        return get(eventId != null ? "/" + eventId : "", userId, role);
    }

    public ResponseEntity<Object> findEvent(Long[] users, String[] states, Long[] categories, String rangeStart,
                                            String rangeEnd, Integer from, Integer size, RoleEnum role) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("users", users);
        pathBuilder.addParameter("states", states);
        pathBuilder.addParameter("categories", categories);
        pathBuilder.addParameter("rangeStart", rangeStart);
        pathBuilder.addParameter("rangeEnd", rangeEnd);
        pathBuilder.addParameter("from", from);
        pathBuilder.addParameter("size", size);

        if (pathBuilder.isPresent()) {
            return get(pathBuilder.getPath(), role, pathBuilder.getParameters());
        } else {
            return get("", role);
        }
    }

    public ResponseEntity<Object> searchEvents(String text, Long[] categories, Boolean paid, String rangeStart,
                                               String rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                               Integer size, RoleEnum role) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("text", text);
        pathBuilder.addParameter("categories", categories);
        pathBuilder.addParameter("paid", paid);
        pathBuilder.addParameter("rangeStart", rangeStart);
        pathBuilder.addParameter("rangeEnd", rangeEnd);
        pathBuilder.addParameter("onlyAvailable", onlyAvailable);
        pathBuilder.addParameter("sort", sort);
        pathBuilder.addParameter("from", from);
        pathBuilder.addParameter("size", size);

        if (pathBuilder.isPresent()) {
            return get("/search" + pathBuilder.getPath(), role, pathBuilder.getParameters());
        } else {
            return get("/search", role);
        }
    }

    public ResponseEntity<Object> findEvents(Long userId, Integer from, Integer size, RoleEnum role) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("from", from);
        pathBuilder.addParameter("size", size);
        if (pathBuilder.isPresent()) {
            return get(pathBuilder.getPath(), userId, role, pathBuilder.getParameters());
        } else {
            return get("", userId, role);
        }
    }

    public ResponseEntity<Object> publishEvent(Long eventId, RoleEnum role) {
        return patch("/" + eventId + "/publish", role);
    }

    public ResponseEntity<Object> rejectEvent(Long eventId, RoleEnum role) {
        return patch("/" + eventId + "/reject", role);
    }

    public ResponseEntity<Object> editEvent(EventRequestDto requestDto, Long eventId, RoleEnum role) {
        return put("/" + eventId, role, requestDto);
    }

    public ResponseEntity<Object> cancelEvent(Long eventId, Long userId, RoleEnum role) {
        return patch("/" + eventId, userId, role);
    }
}
