package ru.explorewithme.exception;

import java.sql.SQLException;

public class IdException extends RuntimeException {
    public IdException(String s) {
            super(s);
        }
}
