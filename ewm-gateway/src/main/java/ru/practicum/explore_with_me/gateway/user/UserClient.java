package ru.practicum.explore_with_me.gateway.user;

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
import ru.practicum.explore_with_me.gateway.user.dto.NewUserRequestDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${server.ewm.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createUser(NewUserRequestDto requestDto, RoleEnum role) {
        return post("", role, requestDto);
    }

    public ResponseEntity<Object> removeUser(Long userId, RoleEnum role) {
        return delete("/" + userId, role);
    }

    public ResponseEntity<Object> findUser(Long[] ids, Integer from, Integer size, RoleEnum role) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("ids", ids);
        pathBuilder.addParameter("from", from);
        pathBuilder.addParameter("size", size);
        if (pathBuilder.isPresent()) {
            return get(pathBuilder.getPath(), role, pathBuilder.getParameters());
        } else {
            return get("", role);
        }
    }
}
