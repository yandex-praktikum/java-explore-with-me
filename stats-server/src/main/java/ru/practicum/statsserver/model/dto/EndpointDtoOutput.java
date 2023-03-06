package ru.practicum.statsserver.model.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EndpointDtoOutput {
        private String app;
        private String uri;
        private long hits;
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                EndpointDtoOutput that = (EndpointDtoOutput) o;

                if (!Objects.equals(app, that.app)) return false;
                return Objects.equals(uri, that.uri);
        }
}
