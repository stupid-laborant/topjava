package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        /*List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));*/
        System.out.println(filteredByCyclesOptimized(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    /*public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        for (UserMeal meal: meals) {
            LocalDate currentDate = meal.getLocalDate();
            totalCaloriesPerDay.put(currentDate, totalCaloriesPerDay.getOrDefault(currentDate, 0)+meal.getCalories());
        }
        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        for (UserMeal meal: meals) {
            LocalTime currentTime = meal.getLocalTime();
            LocalDate currentDate = meal.getLocalDate();
            if (TimeUtil.isBetweenHalfOpen(currentTime, startTime, endTime)) {
                filteredMeals.add(new UserMealWithExcess(meal, totalCaloriesPerDay.get(currentDate)>caloriesPerDay));
            }
        }
        return filteredMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesPerDay = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate,
                        Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal, totalCaloriesPerDay.get(userMeal.getLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }*/

    public static List<UserMealWithExcess> filteredByCyclesOptimized(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        Map<LocalDate, AtomicBoolean> exceededMap = new HashMap<>();
        List<UserMealWithExcess> filteredList = new ArrayList<>();

        for (UserMeal meal: meals) {
            LocalDate currentDate = meal.getLocalDate();
            Integer currentCalories = totalCaloriesPerDay.getOrDefault(currentDate, 0) + meal.getCalories();
            totalCaloriesPerDay.put(currentDate, currentCalories);
            AtomicBoolean isExceeded = exceededMap.getOrDefault(currentDate, new AtomicBoolean(false));
            isExceeded.set(currentCalories>caloriesPerDay);
            exceededMap.put(currentDate, isExceeded);
            if (TimeUtil.isBetweenHalfOpen(meal.getLocalTime(), startTime, endTime)) {
                filteredList.add(new UserMealWithExcess(meal, isExceeded));
            }
        }
        return filteredList;
    }


}
