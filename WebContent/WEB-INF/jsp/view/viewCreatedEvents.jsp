<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data.EventItem"%>
<%
  String redirect = "viewCreatedEvents";
  pageContext.setAttribute("redirect", redirect);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Created Events</title>
</head>
<body>
Here's all the events you've created: <br />
<c:forEach var="events" items="${events}">
  Event Name: <c:out value="${events.getEventName()}" /><br /> 
  Date Created: <c:out value="${events.getCreateDateTime()}" /><br /> 
  Start Date: <c:out value="${events.getStartDateTime()}" /><br />
  <c:out value="${events.constructLikeForHTML(userId,redirect)}" escapeXml="false"/><br />
  <br />
</c:forEach>
</body>
</html>