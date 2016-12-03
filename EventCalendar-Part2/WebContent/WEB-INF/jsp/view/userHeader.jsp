<%@ page import="user.User"%>
<%

  User user = (User)session.getAttribute("LoginUserItem");
  
  if (user != null)
  {
%>

<a href="?action=home">Home</a> | 
<a href="?action=createNewEvent">Create New Event</a> | 
<a href="?action=viewCreatedEvents">View Created Events</a> | 
<a href="?action=viewLikedEvents">View Liked Events</a> |
<a href="logout">Logout</a><br />

<br />
<%
  long userId = user.getId();
  String username = user.getUsername();

  pageContext.setAttribute("username", username);
  pageContext.setAttribute("userId", userId);
  }
%>