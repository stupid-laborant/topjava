package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    public static final List<Meal> meals = Arrays.asList(
            new Meal(100002, LocalDateTime.of(2021, Month.JUNE, 30, 10, 0), "Завтрак", 500),
            new Meal(100003, LocalDateTime.of(2021, Month.JUNE, 30, 13, 0), "Обед", 1000),
            new Meal(100004, LocalDateTime.of(2021, Month.JUNE, 30, 20, 0), "Ужин", 500),
            new Meal(100005, LocalDateTime.of(2021, Month.JULY, 1, 0, 0), "Еда на граничное значение", 100),
            new Meal(100006, LocalDateTime.of(2021, Month.JULY, 1, 10, 0), "Завтрак", 1000),
            new Meal(100007, LocalDateTime.of(2021, Month.JULY, 1, 13, 0), "Обед", 500),
            new Meal(100008, LocalDateTime.of(2021, Month.JULY, 1, 20, 0), "Ужин", 410)
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "new meal", 1499);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meals.get(0));
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 29, 9, 59));
        updated.setDescription("Сегодняк");
        updated.setCalories(501);
        return updated;
    }


}
