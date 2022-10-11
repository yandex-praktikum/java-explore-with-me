package ru.practicum.ewmservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class EventClient extends BaseClient {
    @Value("emw-service")
    private String appName;

    @Autowired
    public EventClient(@Value("http://localhost:9090") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .build()
        );
    }

    public Object getViews(String uri) {
        log.debug("ewm-service отправил GET запрос: {} на state-server", uri);
        return get("/hit?uri=" + uri).getBody();
    }

    public void addHit(HttpServletRequest request) {
        log.debug("ewm-service отправил POST запрос: {} на stats-server", request.getRequestURI());
        post("/hit", new HitDto(appName, request.getRequestURI(), request.getRemoteAddr()));
    }
}
