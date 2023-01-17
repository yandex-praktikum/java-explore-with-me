package ru.practicum.explore_with_me.gateway.compilation;

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
import ru.practicum.explore_with_me.gateway.compilation.dto.NewCompilationDto;

import java.util.Optional;

@Service
public class CompilationClient extends BaseClient {
    private static final String API_PREFIX = "/compilations";

    @Autowired
    CompilationClient(@Value("${server.ewm.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> createCompilation(NewCompilationDto requestDto, RoleEnum role) {
        return post("", role, requestDto);
    }

    public ResponseEntity<Object> getCompilation(Long compId, RoleEnum role) {
        return get(compId != null ? "/" + compId : "", role);
    }

    public ResponseEntity<Object> removeCompilation(Long compId, RoleEnum role) {
        return delete("/" + compId, role);
    }

    public ResponseEntity<Object> addEvent(Long compId, Long eventId, RoleEnum role) {
        return patch("/" + compId + "/events/" + eventId, role);
    }

    public ResponseEntity<Object> removeEvent(Long compId, Long eventId, RoleEnum role) {
        return delete("/" + compId + "/events/" + eventId, role);
    }

    public ResponseEntity<Object> findCompilations(Optional<Boolean> pinned, RoleEnum role,
                                                   Integer from, Integer size) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("pinned", pinned.orElse(false));
        pathBuilder.addParameter("from", from);
        pathBuilder.addParameter("size", size);
        if (pathBuilder.isPresent()) {
            return get(pathBuilder.getPath(), role, pathBuilder.getParameters());
        } else {
            return get("", role);
        }
    }

    public ResponseEntity<Object> pinCompilation(Long compId, RoleEnum role) {
        return patch("/" + compId + "/pin", role);
    }

    public ResponseEntity<Object> unpinCompilation(Long compId, RoleEnum role) {
        return delete("/" + compId + "/pin", role);
    }
}
