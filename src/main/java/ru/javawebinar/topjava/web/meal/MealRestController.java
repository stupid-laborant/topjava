package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = authUserId();
        log.info("get all meals for user {} with filters", userId);
        startTime = DateTimeUtil.checkDateOrTime(startTime, LocalTime.MIN);
        endTime = DateTimeUtil.checkDateOrTime(endTime, LocalTime.MAX);
        return new ArrayList<>(getFilteredTos(service.getFiltered(userId, startDate, endDate), DEFAULT_CALORIES_PER_DAY, startTime, endTime));
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("get all meals for user {}", userId);
        return new ArrayList<>(getTos(service.getAll(userId), DEFAULT_CALORIES_PER_DAY));
    }

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update meal {} with id={}", meal, id);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public Meal get(int id) {
        log.info("get meal id={}", id);
        return service.get(id, authUserId());
    }

}