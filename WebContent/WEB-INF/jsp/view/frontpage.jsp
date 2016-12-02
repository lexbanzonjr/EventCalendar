<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="user.User"%>
<%
  String redirect = "frontpage";
  pageContext.setAttribute("redirect", redirect);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>User Front Page</title>
</head>
<body>
Welcome <c:out value="${username}" /> (<c:out value="${userId}" />)<br />
Here's all the events: <br />

<%@ include file="ListEvents.jsp" %>

</body>
</html>