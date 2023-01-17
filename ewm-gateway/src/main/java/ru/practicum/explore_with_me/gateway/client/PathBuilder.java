package ru.practicum.explore_with_me.gateway.client;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PathBuilder {
    StringBuilder pathBuilder;
    Map<String, Object> parameters;

    public PathBuilder() {
        this.pathBuilder = new StringBuilder();
        this.parameters = new HashMap<>();
    }

    public void addParameter(String key, Object value) {
        if (value != null) {
            pathBuilder.append(pathBuilder.length() > 0 ? "&" : "").append(key).append("={").append(key).append("}");
            parameters.put(key, value);
        }
    }

    public String getPath() {
        if (pathBuilder.length() > 0) {
            return "?" + pathBuilder;
        } else {
            return "";
        }
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public boolean isPresent() {
        return parameters.size() > 0;
    }
}
