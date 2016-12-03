<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  session.setAttribute("currentPage", "viewLikedEvents");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>View your liked events</title>
</head>
<body>
  <c:forEach items="${eventList}" var="event">
    Owner Name: ${event.getOwnerName()} <br>
    Event ID: ${event.getEventId()} <br>
    Event Name: ${event.getEventName()} <br>
    Start Date/Time: ${event.getStartDateTime()} <br>    
    ${event.getData()} <br> 
    <br>
  </c:forEach>
</body>
</html>