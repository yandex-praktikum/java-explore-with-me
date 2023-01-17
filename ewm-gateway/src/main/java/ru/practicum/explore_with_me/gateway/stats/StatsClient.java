package ru.practicum.explore_with_me.gateway.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore_with_me.gateway.client.BaseClient;
import ru.practicum.explore_with_me.gateway.client.PathBuilder;
import ru.practicum.explore_with_me.gateway.stats.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;

@Service
public class StatsClient extends BaseClient {
    private final String hitPathPrefix;
    private final String statsPathPrefix;


    @Autowired
    StatsClient(@Value("${server.stat.url}") String serverUrl, RestTemplateBuilder builder,
                       @Value("${server.hitPathPrefix}")String hitPathPrefix,
                       @Value("${server.statsPathPrefix}")String statsPathPrefix) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
        this.hitPathPrefix = hitPathPrefix;
        this.statsPathPrefix = statsPathPrefix;
    }

    public ResponseEntity<Object> getStats(String start, String end, String[] uris, Boolean uniq) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("start", start);
        pathBuilder.addParameter("end", end);
        pathBuilder.addParameter("uris", uris);
        pathBuilder.addParameter("uniq", uniq);
        if (pathBuilder.isPresent()) {
            return get(statsPathPrefix + pathBuilder.getPath(), pathBuilder.getParameters());
        } else {
            return get(statsPathPrefix);
        }
    }

    public ResponseEntity<Object> createHit(EndpointHitDto requestDto) {
        return post(hitPathPrefix, requestDto);
    }

    public void createHit(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        EndpointHitDto endpointHit = new EndpointHitDto(uri, ip);
        post(hitPathPrefix, endpointHit);
    }
}
