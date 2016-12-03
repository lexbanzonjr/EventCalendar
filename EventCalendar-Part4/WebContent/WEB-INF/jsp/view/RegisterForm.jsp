<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="registerForm" type="user.RegisterForm" --%>
<%
  session.setAttribute("currentPage", "registerForm");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Register Form</title>
  </head>
  <body>
    <form:form method="post" modelAttribute="registerForm">
      <form:label path="username">Username</form:label>   
      <form:input path="username" /><br>
      <form:label path="password">Password</form:label>  
      <form:password path="password" /><br>
      <br>
      <input type="submit" value="Save" />
    </form:form>
  </body>
</html>