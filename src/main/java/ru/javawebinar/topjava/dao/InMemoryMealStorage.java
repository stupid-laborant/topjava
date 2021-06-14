package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealStorage implements MealStorage{

    private final Map<Integer, Meal> STORAGE = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal get(int id) {
        return STORAGE.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        Integer id = meal.getId();
        if (id != null) {
            return STORAGE.put(id, meal);
        }
        id = counter.incrementAndGet();
        meal.setId(id);
        STORAGE.put(id, meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        STORAGE.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(STORAGE.values());
    }
}
