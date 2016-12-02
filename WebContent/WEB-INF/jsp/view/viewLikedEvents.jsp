<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String redirect = "viewLikedEvents";
  pageContext.setAttribute("redirect", redirect);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Liked Events</title>
</head>
<body>
Here's all the events you liked: <br />
<%@ include file="ListEvents.jsp" %>
</body>
</html>