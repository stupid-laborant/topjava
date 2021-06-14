package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.InMemoryMealStorage;
import ru.javawebinar.topjava.dao.MealStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    private MealStorage mealStorage = new InMemoryMealStorage();

    @Override
    public void init() throws ServletException {
        mealStorage.save(new Meal(null,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealStorage.save(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealStorage.save(new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealStorage.save(new Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealStorage.save(new Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealStorage.save(new Meal(null, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            mealList(req, resp);
        } else if (action.equals("new")||action.equals("edit")) {
            editForm(req, resp);
        } else if (action.equals("delete")) {
            delete(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String stringId = req.getParameter("id");
        Integer id = (stringId == null || stringId.isEmpty())? null : Integer.parseInt(stringId);
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        mealStorage.save(meal);
        resp.sendRedirect("/meals");
    }

    private void mealList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mealList", MealsUtil.filteredByStreams(mealStorage.getAll(),
                LocalTime.of(0, 0),
                LocalTime.of(23, 59),
                2000));
        RequestDispatcher dispatcher = req.getRequestDispatcher("meals.jsp");
        dispatcher.forward(req, resp);
    }

    private void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter("id");
        Integer id = stringId == null ? null : Integer.parseInt(stringId);
        Meal meal;
        if (id != null) {
            meal = mealStorage.get(id);
        } else {
            meal = new Meal(id, LocalDateTime.of(LocalDate.now(), LocalTime.now()),"", 0);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("newForm.jsp");
        req.setAttribute("meal", meal);
        dispatcher.forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        mealStorage.delete(id);
        resp.sendRedirect("/meals");
    }


}
