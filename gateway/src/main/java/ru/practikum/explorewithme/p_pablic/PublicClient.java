package ru.practikum.explorewithme.p_pablic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practikum.explorewithme.client.BaseClient;
import ru.practikum.explorewithme.EventsSort;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static ru.practikum.explorewithme.util.DataToLine.arrToLine;
import static ru.practikum.explorewithme.util.DataToLine.varToLine;

@Service
public class PublicClient extends BaseClient {

    @Autowired
    public PublicClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getEvents(Optional<String> text,
                                            Optional<String[]> categories,
                                            Optional<Boolean> paid,
                                            Optional<String> rangeStart,
                                            Optional<String> rangeEnd,
                                            Boolean onlyAvailable,
                                            EventsSort eventsSort,
                                            Integer from,
                                            Integer size,
                                            HttpServletRequest oldRequest) {
        Map<String, Object> parameters = Map.of(
                "text", varToLine(text),
                "categories", arrToLine(categories),
                "paid", varToLine(paid),
                "rangeStart", varToLine(rangeStart),
                "rangeEnd", varToLine(rangeEnd),
                "onlyAvailable", onlyAvailable,
                "eventsSort", eventsSort,
                "from", from,
                "size", size
        );

        return get("events?text={text}&categories={categories}&paid={paid}" +
                "&rangeStart={rangeStart}&rangeEnd={rangeEnd}" +
                "&onlyAvailable={onlyAvailable}&eventsSort={eventsSort}&from={from}&size={size}", oldRequest, parameters);
    }

    public ResponseEntity<Object> getEvent(long id, HttpServletRequest oldRequest) {
        return get("events/" + id, oldRequest);
    }

    public ResponseEntity<Object> getCompilations(Optional<Boolean> pinned, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "pinned", varToLine(pinned),
                "from", from,
                "size", size
        );
        return get("compilations?pinned={pinned}&from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> getCompilation(long compId) {
        return get("compilations/" + compId);
    }

    public ResponseEntity<Object> getCategories(Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("categories?from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> getCategory(long catId) {
        return get("categories/" + catId);
    }
}
