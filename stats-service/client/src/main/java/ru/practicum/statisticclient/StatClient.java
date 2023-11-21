package ru.practicum.statisticclient;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statdto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public class StatClient {
    private final RestTemplate template;

    public StatClient(@Value("${server.url}") String url,
                      @NotNull RestTemplateBuilder template) {
        this.template = template
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public void addHit(@NotNull HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto(
                null,
                "main",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()
        );
        template.postForEntity("/hit",
                new HttpEntity<>(endpointHitDto),
                EndpointHitDto.class);
    }

    public ResponseEntity<List<ViewStats>> getStat(String start,
                                                   String end,
                                                   List<String> uris,
                                                   boolean unique) {
        return template.exchange("/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                HttpMethod.GET,
                getHttpEntity(null),
                new ParameterizedTypeReference<>() {
                },
                start, end, uris, unique);
    }

    private <T> @NotNull HttpEntity<T> getHttpEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return dto == null ? new HttpEntity<>(headers) : new HttpEntity<>(dto, headers);
    }
}