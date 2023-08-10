package ru.practicum.stats_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.stats_common.CommonUtils;
import ru.practicum.stats_common.model.EndpointHit;
import ru.practicum.stats_server.controller.StatsController;
import ru.practicum.stats_server.service.StatsService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatsControllerTest {
    private final ObjectMapper mapper;
    private final MockMvc mvc;

    @MockBean
    private StatsService statsService;

    private EndpointHit endpointHit;
    private String start;
    private String end;
    private List<String> uris;
    private Boolean unique;

    @Nested
    class AddHit {
        @BeforeEach
        public void beforeEach() {
            endpointHit = EndpointHit.builder()
                    .app("test APP")
                    .uri("/test/uri/1")
                    .ip("127.0.0.1")
                    .timestamp("2020-01-01 10:00:00")
                    .build();
        }

        @Test
        public void shouldAdd() throws Exception {
            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            verify(statsService, times(1)).addHit(ArgumentMatchers.eq(endpointHit));
        }

        @Test
        public void shouldThrowExceptionIfAppIsNull() throws Exception {
            endpointHit.setApp(null);

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfAppIsEmpty() throws Exception {
            endpointHit.setApp("");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfAppIsBlank() throws Exception {
            endpointHit.setApp(" ");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfUriIsNull() throws Exception {
            endpointHit.setUri(null);

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfUriIsEmpty() throws Exception {
            endpointHit.setUri("");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfUriIsBlank() throws Exception {
            endpointHit.setUri(" ");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfIpIsNull() throws Exception {
            endpointHit.setIp(null);

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfIpIsEmpty() throws Exception {
            endpointHit.setIp("");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfIpIsBlank() throws Exception {
            endpointHit.setIp(" ");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfTimestampIsNull() throws Exception {
            endpointHit.setTimestamp(null);

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfTimestampIsEmpty() throws Exception {
            endpointHit.setTimestamp("");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfTimestampIsBlank() throws Exception {
            endpointHit.setTimestamp(" ");

            mvc.perform(post(CommonUtils.HIT_ENDPOINT)
                            .content(mapper.writeValueAsString(endpointHit))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).addHit(ArgumentMatchers.any());
        }
    }

    @Nested
    class GetStats {
        @BeforeEach
        public void beforeEach() {
            start = "2020-01-01 00:00:00";
            end = "2035-01-01 00:00:00";
            uris = List.of("/test/uri/1", "/test/uri/2");
            unique = true;
        }

        @Test
        public void shouldGet() throws Exception {
            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}&uris={uris}&uris={uris}&unique={unique}",
                            start, end, uris.get(0), uris.get(1), unique)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(statsService, times(1)).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.eq(uris), ArgumentMatchers.eq(unique));
        }

        @Test
        public void shouldGetWithDefaultUnique() throws Exception {
            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}&uris={uris}&uris={uris}",
                            start, end, uris.get(0), uris.get(1))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(statsService, times(1)).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.eq(uris), ArgumentMatchers.eq(false));
        }

        @Test
        public void shouldGetWithoutUris() throws Exception {
            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}&unique={unique}",
                            start, end, unique)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(statsService, times(1)).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.eq(null), ArgumentMatchers.eq(unique));
        }

        @Test
        public void shouldGetWithoutUrisAndUnique() throws Exception {
            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}",
                            start, end, unique)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(statsService, times(1)).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.eq(null), ArgumentMatchers.eq(false));
        }

        @Test
        public void shouldThrowExceptionIfStartNotValid() throws Exception {
            start = "2020-01-01T00:00:00";

            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}&uris={uris}&uris={uris}&unique={unique}",
                            start, end, uris.get(0), uris.get(1), unique)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.any(), ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfEndNotValid() throws Exception {
            end = "2035-01-01T00:00:00";

            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}&uris={uris}&uris={uris}&unique={unique}",
                            start, end, uris.get(0), uris.get(1), unique)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.any(), ArgumentMatchers.any());
        }

        @Test
        public void shouldThrowExceptionIfStartAfterEnd() throws Exception {
            end = "2005-01-01 00:00:00";

            mvc.perform(get(CommonUtils.STATS_ENDPOINT + "?start={start}&end={end}&uris={uris}&uris={uris}&unique={unique}",
                            start, end, uris.get(0), uris.get(1), unique)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(statsService, never()).getStats(ArgumentMatchers.any(), ArgumentMatchers.any(),
                    ArgumentMatchers.any(), ArgumentMatchers.any());
        }
    }
}
