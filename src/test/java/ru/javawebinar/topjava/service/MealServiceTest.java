package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        assertThat(mealService.get(100002, UserTestData.USER_ID)).isEqualTo(MealTestData.meals.get(0));
    }

    @Test
    public void delete() {
        mealService.delete(100002, UserTestData.USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(100002, UserTestData.USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List <Meal> filtered = MealTestData.meals.subList(0,4);
        filtered.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertThat(mealService.getBetweenInclusive(
                LocalDate.of(2021, Month.JUNE, 30),
                LocalDate.of(2021, Month.JUNE, 30),
                UserTestData.USER_ID
        )).isEqualTo(filtered);
    }

    @Test
    public void getAll() {
        List<Meal> all = MealTestData.meals;
        all.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertThat(mealService.getAll(UserTestData.USER_ID)).isEqualTo(all);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        mealService.update(updated, UserTestData.USER_ID);
        assertThat(mealService.get(100002, UserTestData.USER_ID)).isEqualTo(updated);
    }

    @Test
    public void create() {
        Meal created = mealService.create(MealTestData.getNew(), UserTestData.USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertThat(created).isEqualTo(newMeal);
        assertThat(mealService.get(newId, UserTestData.USER_ID)).isEqualTo(newMeal);
    }
}