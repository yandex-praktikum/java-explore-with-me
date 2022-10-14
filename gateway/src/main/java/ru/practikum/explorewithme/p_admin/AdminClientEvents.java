package ru.practikum.explorewithme.p_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practikum.explorewithme.client.BaseClient;
import ru.practikum.explorewithme.EventsSort;
import ru.practikum.explorewithme.dto.in.AdminUpdateEventRequest;

import java.util.Map;
import java.util.Optional;

import static ru.practikum.explorewithme.util.DataToLine.arrToLine;
import static ru.practikum.explorewithme.util.DataToLine.varToLine;


@Service
public class AdminClientEvents extends BaseClient {

    @Autowired
    public AdminClientEvents(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getEvents(Optional<Long[]> users,
                                            Optional<String[]> states,
                                            Optional<Long[]> categories,
                                            Optional<String> rangeStart,
                                            Optional<String> rangeEnd,
                                            boolean onlyAvailable,
                                            EventsSort sort,
                                            Integer from,
                                            Integer size) {
        Map<String, Object> parameters = Map.of(
                "users", arrToLine(users),
                "states", arrToLine(states),
                "categories", arrToLine(categories),
                "rangeStart", varToLine(rangeStart),
                "rangeEnd", varToLine(rangeEnd),
                "onlyAvailable", onlyAvailable,
                "sort", sort,
                "from", from,
                "size", size
        );
        return get("admin/events?users={users}&&states={states}&&categories={categories}" +
                "&&rangeStart={rangeStart}&&rangeEnd={rangeEnd}&&onlyAvailable={onlyAvailable}" +
                "&&sort={sort}&&from={from}&&size={size}", parameters);
    }

    public ResponseEntity<Object> updateEvent(long eventId, AdminUpdateEventRequest dto) {
        return put("admin/events/" + eventId, dto);
    }

    public ResponseEntity<Object> publishEvent(long eventId) {
        return patch("admin/events/" + eventId + "/publish");
    }

    public ResponseEntity<Object> rejectEvent(long eventId) {
        return patch("admin/events/" + eventId + "/reject");
    }
}
