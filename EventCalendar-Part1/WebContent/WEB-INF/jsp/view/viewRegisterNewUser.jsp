<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Created Events</title>
</head>
<body>
<font color="red"><c:out value="${message}" /></font><br />
Please input a new username and a password:<br />
<form method="POST">
<input type="hidden" name="action" value="register"/>
Username: <input type="text" name="username" /> <br />
Password: <input type="password" name="password" /><br />
<input type="submit" value="Submit" />
</body>
</html>