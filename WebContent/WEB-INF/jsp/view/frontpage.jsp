<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data.UserItem"%>
<%@ page import="data.EventItem"%>
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
  Event Name: <c:out value="${events.getEventName()}" /><br /> 
  Date Created: <c:out value="${events.getCreateDateTime()}" /><br /> 
  Start Date: <c:out value="${events.getStartDateTime()}" /><br />
  Owner: <c:out value="${users.get(events.getOwnerId()).getUsername()}" /><br />
  <c:out value="${events.constructLikeForHTML(userId,redirect)}" escapeXml="false"/><br />
  <br />
</c:forEach>
</body>
</html>