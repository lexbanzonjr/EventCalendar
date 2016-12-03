<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="user.User" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%
  String url = request.getScheme() + "://" +
               request.getServerName() + ":" +
               request.getServerPort() + "/EventCalendar-Part4";

  String dashboard = url + "/dashboard";
  pageContext.setAttribute("dashboard", dashboard);
  
  String createNewEvent = url + "/event/createnewevent";
  pageContext.setAttribute("createNewEvent", createNewEvent);

  String viewCreatedEvents = url + "/event/viewcreatedevents";
  pageContext.setAttribute("viewCreatedEvents", viewCreatedEvents);

  String viewLikedEvents = url + "/event/viewlikedevents";
  pageContext.setAttribute("viewLikedEvents", viewLikedEvents);
  
  String logout = url + "/logout/";
  pageContext.setAttribute("logout", logout);
  
  /* Check if user is currently logged in */
  User user = (User)session.getAttribute("User");
  if (user != null)
  {
    pageContext.setAttribute("username", user.getUsername());
%>
You are user: <b><c:out value="${username}" /></b><br>
<a href="<c:out value="${dashboard}" />" >Home</a> | 
<a href="<c:out value="${createNewEvent}" />">Create New Event</a> |
<a href="<c:out value="${viewCreatedEvents}" />">View Created Events</a> |
<a href="<c:out value="${viewLikedEvents}" />">View All Liked Events</a> |
<a href="<c:out value="${logout}" />">Logout</a> |  

<br />
<%}%>
<br />