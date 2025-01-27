package ru.practicum;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class HttpClientStats {
    private static final HttpClient httpClient=HttpClient.newHttpClient();;
    private final String url = "http://localhost:9090";
    private static final String endpointStats = "/stats";
    private static final String endpointHits = "/hits";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EndpointHitDto sendCreateEndpointHit(EndpointHitDto endpointHitDto) throws Exception {
        String request = objectMapper.writeValueAsString(endpointHitDto);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url + endpointHits))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), EndpointHitDto.class);
    }

    public List<ViewStatsDto> sendGetViewStats(LocalDateTime start, LocalDateTime end) throws Exception {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> kvDateTime = UtilityHttpClient.trimLocalDateTime(start, end);

        String queryParams = String.format(
                "start=%s&end=%s",
                URLEncoder.encode(kvDateTime.get("startDate") + " " + kvDateTime.get("startTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(kvDateTime.get("endDate") + " " + kvDateTime.get("endTime"), StandardCharsets.UTF_8)
        );

        sb.append(url).append(endpointStats).append("?").append(queryParams);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), new TypeReference<List<ViewStatsDto>>() {});
    }

    public List<ViewStatsDto> sendGetViewStatsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris) throws Exception {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> kvDateTime = UtilityHttpClient.trimLocalDateTime(start, end);
        String urisString = String.join(",", uris);

        String queryParams = String.format(
                "start=%s&end=%s&uris=%s",
                URLEncoder.encode(kvDateTime.get("startDate") + " " + kvDateTime.get("startTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(kvDateTime.get("endDate") + " " + kvDateTime.get("endTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(urisString, StandardCharsets.UTF_8)
        );
        sb.append(url).append(endpointStats).append("?").append(queryParams);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), new TypeReference<List<ViewStatsDto>>() {});
    }

    public List<ViewStatsDto> sendGetViewStatsWithUnique(LocalDateTime start, LocalDateTime end, Boolean unique) throws Exception {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> kvDateTime = UtilityHttpClient.trimLocalDateTime(start, end);

        String queryParams = String.format(
                "start=%s&end=%s&unique=%s",
                URLEncoder.encode(kvDateTime.get("startDate") + " " + kvDateTime.get("startTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(kvDateTime.get("endDate") + " " + kvDateTime.get("endTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(unique.toString(), StandardCharsets.UTF_8)
        );

        sb.append(url).append(endpointStats).append("?").append(queryParams);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), new TypeReference<List<ViewStatsDto>>() {});
    }

    public List<ViewStatsDto> sendGetViewStatsWithUrisAndUnique(LocalDateTime start, LocalDateTime end,
                                                                List<String> uris, Boolean unique) throws Exception {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> kvDateTime = UtilityHttpClient.trimLocalDateTime(start, end);
        String urisString = String.join(",", uris);

        String queryParams = String.format(
                "start=%s&end=%s&uris=%s&unique=%s",
                URLEncoder.encode(kvDateTime.get("startDate") + " " + kvDateTime.get("startTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(kvDateTime.get("endDate") + " " + kvDateTime.get("endTime"), StandardCharsets.UTF_8),
                URLEncoder.encode(urisString, StandardCharsets.UTF_8),
                URLEncoder.encode(unique.toString(), StandardCharsets.UTF_8)
        );
        sb.append(url).append(endpointStats).append("?").append(queryParams);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), new TypeReference<List<ViewStatsDto>>() {});
    }
}
