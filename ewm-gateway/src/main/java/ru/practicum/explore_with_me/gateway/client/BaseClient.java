package ru.practicum.explore_with_me.gateway.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public class BaseClient {
    private static final String HEADER_USER_ROLE = "X-Explore-With-Me-User-Role";
    private static final String HEADER_USER_ID = "X-Explore-With-Me-User-Id";
    private static final RoleEnum DEFAULT_USER_ROLE = RoleEnum.PUBLIC;
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(String path) {
        return get(path, null, DEFAULT_USER_ROLE,
                null);
    }

    protected ResponseEntity<Object> get(String path, RoleEnum role) {
        return get(path, null, role,
                null);
    }

    protected ResponseEntity<Object> get(String path, Long userId, RoleEnum role) {
        return get(path, userId, role, null);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, null, null, parameters, null);
    }

    protected ResponseEntity<Object> get(String path, RoleEnum role, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, null, role, parameters, null);
    }

    protected ResponseEntity<Object> get(String path, Long userId, RoleEnum role, @Nullable Map<String,
            Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, role, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, @Nullable T body) {
        return post(path, null, DEFAULT_USER_ROLE, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, RoleEnum role, @Nullable T body) {
        return post(path, null, role, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, RoleEnum role) {
        return post(path, userId, role, null, null);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, RoleEnum role, @Nullable T body) {
        return post(path, userId, role, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, RoleEnum role, @Nullable Map<String,
            Object> parameters) {
        return post(path, userId, role, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, RoleEnum role, @Nullable Map<String,
            Object> parameters, @Nullable T body) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, role, parameters, body);
    }

    protected <T> ResponseEntity<Object> put(String path,Long userId, RoleEnum role, @Nullable T body) {
        return put(path, userId, role, null, body);
    }

    protected <T> ResponseEntity<Object> put(String path, RoleEnum role, @Nullable T body) {
        return put(path, null, role, null, body);
    }

    protected <T> ResponseEntity<Object> put(String path, Long userId, RoleEnum role, @Nullable Map<String,
            Object> parameters, @Nullable T body) {
        return makeAndSendRequest(HttpMethod.PUT, path, userId, role, parameters, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, RoleEnum role) {
        return patch(path, null, role, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, Long userId, RoleEnum role) {
        return patch(path, userId, role, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, RoleEnum role, T body) {
        return patch(path, null, role, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, Long userId, RoleEnum role, @Nullable T body) {
        return patch(path, userId, role, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, Long userId, RoleEnum role, @Nullable Map<String,
            Object> parameters, @Nullable T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, role, parameters, body);
    }

    protected ResponseEntity<Object> delete(String path, RoleEnum role) {
        return delete(path, role, null);
    }

    protected ResponseEntity<Object> delete(String path, Long userId, RoleEnum role) {
        return makeAndSendRequest(HttpMethod.DELETE, path, userId, role, null, null);
    }

    protected ResponseEntity<Object> delete(String path, RoleEnum role, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.DELETE, path, null, role, parameters, null);
    }

    public ResponseEntity<Object> actuator() {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(HttpStatus.OK);
        return responseBuilder.body(new UpStatus());
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, Long userId, RoleEnum role,
                                                          @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity;
        if (body != null) {
            requestEntity = new HttpEntity<>(body, defaultHeaders(userId, role));
        } else {
            requestEntity = new HttpEntity<>(defaultHeaders(userId, role));
        }
        ResponseEntity<Object> response;
        try {
            if (parameters != null && parameters.size() > 0) {
                response = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                response = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {

            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(response);
    }

    private HttpHeaders defaultHeaders(Long userId, RoleEnum role) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (role != null) {
            headers.set(HEADER_USER_ROLE, role.name());
        } else {
            headers.set(HEADER_USER_ROLE, DEFAULT_USER_ROLE.name());
        }
        if (userId != null) {
            headers.set(HEADER_USER_ID, String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }
}
