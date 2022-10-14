package ru.practikum.explorewithme.util;

import java.util.Optional;

public class DataToLine {

    private DataToLine() {}

    public static <T> String arrToLine(Optional<T[]> data) {
        StringBuilder str = new StringBuilder("");
        if (data.isPresent()) {
            for (T el : data.get()) {
                str.append(el);
                str.append(",");
            }
            return str.substring(0, str.toString().length() - 1);
        }
        return str.toString();
    }

    public static <T> String varToLine(Optional<T> data) {
        StringBuilder str = new StringBuilder("");
        data.ifPresent(str::append);
        return str.toString();
    }
}
