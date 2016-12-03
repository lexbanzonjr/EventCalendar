<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="data.UserItem"%>
<%@ page import="data.EventItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Create New Event</title>
</head>
<body>
	<form method="POST">
		<input type="hidden" name="action" value="create"/>
		Name<br/>
		<input type="text" name="eventName"><br/><br/>
		Start Date (MM-dd-yyyy HH:mm a)<br/>
    <input type="text" name="startDateTime"><br/><br/>
    End Date (MM-dd-yyyy HH:mm a)<br/>
    <input type="text" name="endDateTime"><br/><br/>
		<input type="submit" value="Submit"/>
	</form>
</body>
</html>