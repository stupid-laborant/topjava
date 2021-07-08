package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "new meal", 1499);
    }


}
