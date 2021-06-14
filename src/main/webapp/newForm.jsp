<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 08.06.2021
  Time: 23:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<html>
<head>
    <title>
    <c:if test="${meal == null}">
        New Meal
    </c:if>
    <c:if test="${meal != null}">
        Edit Meal
    </c:if>
    </title>
</head>
<body>
    <h2>
    <c:if test="${meal == null}">
        New Meal
    </c:if>
    <c:if test="${meal != null}">
        Edit Meal
    </c:if>
    </h2>
    <form method="post" action="meals">
    <c:if test="${meal != null}">
        <input type="hidden" name="id" value="<c:out value='${meal.id}'/>"/>
    </c:if>
    <label>
        Date and Time
        <input type="datetime-local" name="dateTime" value="<c:out value='${meal.dateTime}'/>"/>
    </label>

    <label>
        Description
        <input type="text" name="description" value="<c:out value='${meal.description}'/>"/>
    </label>

    <label>
        Calories
        <input type="text" name="calories" value="<c:out value="${meal.calories}"/>"/>
    </label>

    <button type="submit" class="btn btn-success">Save</button>
    </form>
</body>
</html>
