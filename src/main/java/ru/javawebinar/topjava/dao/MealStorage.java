package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    Meal get(int id);
    Meal save(Meal meal);
    void delete(int id);
    List<Meal> getAll();
}
