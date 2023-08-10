package ru.practicum.stats_common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EndpointHit {
    @NotBlank
    String app;

    @NotBlank
    String uri;

    @NotBlank
    String ip;

    @NotBlank
    String timestamp;

    @Override
    public String toString() {
        return "EndpointHit{" +
                "app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointHit that = (EndpointHit) o;
        return Objects.equals(app, that.app) && Objects.equals(uri, that.uri) && Objects.equals(ip, that.ip) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, ip, timestamp);
    }
}
