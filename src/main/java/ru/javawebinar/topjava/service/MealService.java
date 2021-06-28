package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository mealRepository;

    private final UserRepository userRepository;

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public Meal create(Meal meal, int userId) {
        checkNotFoundWithId(userRepository.get(userId), userId);

        return mealRepository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        if (checkNotFoundWithId(userRepository.get(userId), userId) != null) {
            mealRepository.save(meal, userId);
        }
    }

    public void delete(int mealId, int userId) {
        if (checkNotFoundWithId(userRepository.get(userId), userId) != null) {
            mealRepository.delete(mealId, userId);
        }
    }

    public Meal get(int mealId, int userId) {
        if (checkNotFoundWithId(userRepository.get(userId), userId) != null) {
            return checkNotFoundWithId(mealRepository.get(mealId, userId), mealId);
        }
        return null;
    }

    public List<Meal> getAll(int userId) {
        if (checkNotFoundWithId(userRepository.get(userId), userId) != null) {
            return new ArrayList<>(mealRepository.getAll(userId));
        }
        return new ArrayList<>();
    }

    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        if (checkNotFoundWithId(userRepository.get(userId), userId) != null) {
            startDate = DateTimeUtil.checkDateOrTime(startDate, LocalDate.MIN);
            endDate = DateTimeUtil.checkDateOrTime(endDate, LocalDate.MAX);
            return new ArrayList<>(mealRepository.getFiltered(userId, startDate, endDate));
        }
        return new ArrayList<>();
    }
}