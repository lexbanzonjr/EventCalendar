<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String redirect = "frontpage";

  long userId = user.getId();
  String username = user.getUsername();
  pageContext.setAttribute("redirect", redirect);
  pageContext.setAttribute("username", username);
  pageContext.setAttribute("userId", userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>User Front Page</title>
</head>
<body>
Welcome <c:out value="${username}" /> (<c:out value="${userId}" />)<br />
Here's all the events: <br />
<c:forEach var="events" items="${events}">
    Owner Name: ${events.getOwnerName()} <br>
    Event ID: ${events.getEventId()} <br>
    Event Name: ${events.getEventName()} <br>
    Start Date/Time: ${events.getStartDateTime()} <br>
    End Date/Time: ${events.getEndDateTime()} <br>
    ${events.getData()} <br>
    <br>
  </c:forEach>
</body>
</html>