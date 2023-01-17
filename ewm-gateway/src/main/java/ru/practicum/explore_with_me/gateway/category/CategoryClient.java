package ru.practicum.explore_with_me.gateway.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore_with_me.gateway.category.dto.CategoryDto;
import ru.practicum.explore_with_me.gateway.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.gateway.client.BaseClient;
import ru.practicum.explore_with_me.gateway.client.PathBuilder;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;

@Service
public class CategoryClient extends BaseClient {
    private static final String API_PREFIX = "/categories";

    @Autowired
    public CategoryClient(@Value("${server.ewm.url}") String serverUrl,
                          RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> createCategory(NewCategoryDto requestDto, RoleEnum role) {
        return post("", role, requestDto);
    }

    public ResponseEntity<Object> editCategory(CategoryDto requestDto, RoleEnum role) {
        return patch("", role, requestDto);
    }

    public ResponseEntity<Object> getById(Long catId, RoleEnum role) {
        return get(catId != null ? "/" + catId : "", role);
    }

    public ResponseEntity<Object> removeCategory(Long catId, RoleEnum role) {
        return delete("/" + catId, role);
    }

    public ResponseEntity<Object> findCategories(Integer from, Integer size, RoleEnum role) {
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder.addParameter("from", from);
        pathBuilder.addParameter("size", size);
        if (pathBuilder.isPresent()) {
            return get(pathBuilder.getPath(), role, pathBuilder.getParameters());
        } else {
            return get("", role);
        }
    }
}
