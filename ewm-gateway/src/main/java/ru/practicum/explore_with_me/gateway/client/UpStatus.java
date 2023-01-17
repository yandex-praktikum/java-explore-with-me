package ru.practicum.explore_with_me.gateway.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpStatus {
    String status = "UP";
}
