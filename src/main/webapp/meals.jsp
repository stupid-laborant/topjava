<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 07.06.2021
  Time: 2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<html>

<head>
    <title>Meals</title>
</head>
<body>
<h2>Meals</h2>

<div align="left">
    <a href="/meals?action=new">Add new meal</a>
</div>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Meals</h2></caption>
        <tr>
            <th>Date&Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="meal" items="${mealList}">
            <tr style="background-color:${ (meal.excess == true ? 'red' : 'greenyellow')}">
                <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
                    </td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Edit</a></td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
