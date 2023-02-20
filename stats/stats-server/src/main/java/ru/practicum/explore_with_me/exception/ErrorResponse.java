package ru.practicum.explore_with_me.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
    private final String error;

    public String getError() {
        return error;
    }
}
