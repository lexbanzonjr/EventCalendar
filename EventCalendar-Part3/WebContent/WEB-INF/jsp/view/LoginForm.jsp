<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  session.setAttribute("currentPage", "loginForm");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Login</title>
  </head>
  <body>
    <form:form method="post" modelAttribute="loginForm">
      <form:label path="username">Username</form:label>   
      <form:input path="username" /><br>
      <form:label path="password">Password</form:label>  
      <form:password path="password" /><br>
      <br>
      <input type="submit" value="Login" />
    </form:form>
    <a href="register">Click here to register</a> <br>
<%
  String error = (String) session.getAttribute("error");
  if (error != null)
  {
%>
    <font color="red">ERROR:<c:out value="${ error }" /></font>
<%
    session.removeAttribute("error");
  }
%>
  </body>
</html>