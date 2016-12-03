<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data.EventDataSource"%>
<%@ page import="data.EventItem"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login</title>
</head>
<body>
<font color="red"><c:out value="${message}" /></font><br />
Please login:<br />
<form method="POST">
<input type="hidden" name="action" value="login"/>
Username: <input type="text" name="username" /> <br />
Password: <input type="password" name="password" /><br />
<input type="submit" value="Submit" /><br />
<a href="register">Click here to register a new user</a>
</form>
</body>
</html>