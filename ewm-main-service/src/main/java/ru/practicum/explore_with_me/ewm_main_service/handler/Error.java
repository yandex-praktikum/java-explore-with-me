package ru.practicum.explore_with_me.ewm_main_service.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Error {

    String module;
    String field;
    String message;

    public Error(String field, String message) {
        this.module  = "ewm-main-service";
        this.field = field;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Service: %s. Field: %s. Message: %s.", module, field, message);
    }
}
