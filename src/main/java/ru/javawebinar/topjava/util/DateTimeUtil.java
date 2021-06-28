package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Temporal & Comparable<T>> boolean isBetweenHalfOpen(T temp, T start, T end) {
        return temp.compareTo(start) >=0 && temp.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static <T extends Temporal> T checkDateOrTime(T t, T defaultValue) {
        return t == null ? defaultValue : t;
    }
}

