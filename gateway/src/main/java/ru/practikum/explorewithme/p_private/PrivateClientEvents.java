package ru.practikum.explorewithme.p_private;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practikum.explorewithme.client.BaseClient;
import ru.practikum.explorewithme.dto.in.NewEventDto;
import ru.practikum.explorewithme.dto.in.UpdateEventRequest;

import java.util.Map;

@Service
public class PrivateClientEvents extends BaseClient {

    @Autowired
    public PrivateClientEvents(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getEvents(long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("users/" + userId + "/events?from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> updateEvent(long userId, UpdateEventRequest updateEventRequest) {
        return patch("users/" + userId + "/events", updateEventRequest);
    }

    public ResponseEntity<Object> createEvent(long userId, NewEventDto newEventDto) {
        return post("users/" + userId + "/events", newEventDto);
    }

    public ResponseEntity<Object> getEvent(long userId, long eventId) {
        return get("users/" + userId + "/events/" + eventId);
    }

    public ResponseEntity<Object> cancelEvent(long userId, long eventId) {
        return patch("users/" + userId + "/events/" + eventId);
    }

    public ResponseEntity<Object> getEventRequests(long userId, long eventId) {
        return get("users/" + userId + "/events/" + eventId + "/requests");
    }

    public ResponseEntity<Object> confirmEventRequest(long userId, long eventId, long reqId) {
        return patch("users/" + userId + "/events/" + eventId + "/requests/" + reqId + "/confirm");
    }

    public ResponseEntity<Object> rejectEventRequest(long userId, long eventId, long reqId) {
        return patch("users/" + userId + "/events/" + eventId + "/requests/" + reqId + "/reject");
    }
}
