package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        for (UserMeal meal: meals) {
            LocalDate currentDate = meal.getDateTime().toLocalDate();
            totalCaloriesPerDay.put(currentDate, totalCaloriesPerDay.getOrDefault(currentDate, 0)+meal.getCalories());
        }
        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        for (UserMeal meal: meals) {
            LocalTime currentTime = meal.getDateTime().toLocalTime();
            LocalDate currentDate = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetweenHalfOpen(currentTime, startTime, endTime)) {
                filteredMeals.add(new UserMealWithExcess(meal, totalCaloriesPerDay.get(currentDate)>caloriesPerDay));
            }
        }
        return filteredMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> totalCaloriesPerDay = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate,
                        Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal, totalCaloriesPerDay.get(userMeal.getLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOptimized(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream().collect(new MealExcessCollector(caloriesPerDay));

    }

    private static class MealExcessCollector implements Collector<UserMeal, HashMap<UserMeal, Integer>, List<UserMealWithExcess>> {
        int caloriesPerDay;

        public MealExcessCollector(int caloriesPerDay) {
            this.caloriesPerDay = caloriesPerDay;
        }

        @Override
        public Supplier<HashMap<UserMeal, Integer>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<HashMap<UserMeal, Integer>, UserMeal> accumulator() {
            return (map, meal) -> {
                map.put(meal, map.getOrDefault(meal, 0)+meal.getCalories());
            };
        }

        @Override
        public BinaryOperator<HashMap<UserMeal, Integer>> combiner() {
            return (l, r) -> {l.putAll(r); return l;};
        }

        @Override
        public Function<HashMap<UserMeal, Integer>, List<UserMealWithExcess>> finisher() {

            return map -> map.entrySet().stream()
                    .map(entry -> new UserMealWithExcess(entry.getKey(), entry.getValue()>caloriesPerDay))
                    .collect(Collectors.toList());
        }

        @Override
        public Set<Characteristics> characteristics() {
            return null;
        }
    }
}
