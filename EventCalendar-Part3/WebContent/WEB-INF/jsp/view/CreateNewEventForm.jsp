<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  session.setAttribute("currentPage", "createNewEventForm");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Create New Event</title>
  </head>
  <body>
    <form:form method="post" modelAttribute="createNewEventForm">
      <form:label path="eventName">Event Name</form:label>
      <form:input path="eventName" /><br>
      
      <form:label path="startDateTime" /> Start Date/Time <br>
      <form:label path="startMonth" /> MM 
      <form:input path="startMonth" size="2"/> 
      <form:label path="startDay"/> DD
      <form:input path="startDay" size="2"/> 
      <form:label path="startYear" /> YYYY
      <form:input path="startYear" size="4"/><br>
      
      <form:label path="endDateTime" /> End Date/Time <br>
      <form:label path="endMonth" /> MM 
      <form:input path="endMonth" size="2"/> 
      <form:label path="endDay"/> DD
      <form:input path="endDay" size="2"/> 
      <form:label path="endYear" /> YYYY
      <form:input path="endYear" size="4"/>
      <br>
      <input type="submit" value="Create" />
    </form:form>  
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