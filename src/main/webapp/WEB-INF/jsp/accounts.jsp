<%--suppress XmlDefaultAttributeValue --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="ua.com.javarush.quest.kossatyy.questdelta.entity.Role" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>

<body>
<c:import url="/WEB-INF/jsp/parts/header.jsp"/>
<div class="container mt-3">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title text-center">Accounts list</h4>
            <c:if test="${Role.ADMIN == sessionScope.role}">
                <a href="${pageContext.request.contextPath}/signup">Create new user</a>
            </c:if>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>Role</th>
                    <c:if test="${Role.ADMIN == sessionScope.role}">
                        <th>Edit</th>
                        <th>Delete</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.users}" varStatus="index">
                    <tr>
                        <th scope="row"><c:out value="${index.count + 10 * requestScope.page}"/></th>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <c:if test="${Role.ADMIN == sessionScope.role}">
                            <td>
                                <a href="${pageContext.request.contextPath}/update?login=${user.login}">Edit</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/accounts/delete?login=${user.login}">Delete</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <ul class="pagination">
                <fmt:parseNumber value="${requestScope.pageCount}" var="pages" integerOnly="TRUE" type="NUMBER"/>
                <c:forEach var="i" begin="0" end="${pages}">
                    <li class="${requestScope.page == i ? "page-item active" : "page-item"}">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/accounts?page=${i}">
                            <c:out value="${i+1}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <c:if test="${not empty requestScope.error}">
        <div class="form-floating text-danger my-3 p-2">
            <p class="text-center m-0">${requestScope.error}</p>
        </div>
    </c:if>
</div>


<c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
<c:import url="/WEB-INF/jsp/parts/scripts.jsp"/>
</body>
</html>