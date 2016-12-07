package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import event.Event;
import event.JdbcEventDAO;
import likes.JdbcLikesDAO;
import likes.Likes;
import user.JdbcUserDAO;
import user.User;

@Controller
public class CalendarController
{  
  
  private JdbcEventDAO jdbcEventDAO;
  private JdbcLikesDAO jdbcLikesDAO;
  private JdbcUserDAO jdbcUserDAO;
  
  @RequestMapping("/")
  public View Home(HttpSession session, Map<String, Object> model)
  {
    User loginUser = (User) session.getAttribute("User");
    if (loginUser == null)
    {
      model.put("loginURL", "login");
      return new RedirectView("/login/{loginURL}", true);
    }
    
    model.put("dashboardURL", "dashboard");
    return new RedirectView("/{dashboardURL}", true);
  }
  
  @RequestMapping("dashboard")
  public String Dashboard(Map<String, Object> model, HttpSession session, HttpServletResponse response) throws Exception
  {          
    User loginUser = (User) session.getAttribute("User");
    if (loginUser == null)
    {
      response.sendRedirect("login/login");
    }
    // Get all events
    List<Event> eventList = jdbcEventDAO.findAll();      
    for (Event event : eventList)
    { 
      // Set owner name in the event
      int ownerId = event.getOwnerId();
      User user = jdbcUserDAO.findUserById(ownerId);
      String ownerName = user.getUsername();
      event.setOwnerName(ownerName);
      
      // Set like in the event
      int eventId = event.getEventId();
      int userId = loginUser.getId();
      Likes like = jdbcLikesDAO.findLike(userId, eventId);
      if (like != null)
      {
        event.setLiked(true);
        event.setData("<a href=\"event/dislike/" + eventId + "\">Dislike</a> ");
      }
      else
      {
        event.setData("<a href=\"event/like/" + eventId + "\">Like</a> ");
      }
    }
    model.put("eventList", eventList);    
    
    // Go to user's dashboard
    return "Dashboard";
  }  
  
  public void setJdbcEventDAO(JdbcEventDAO jdbcEventDAO)  
  {
    this.jdbcEventDAO = jdbcEventDAO;
  }
  
  public void setJdbcLikesDAO(JdbcLikesDAO jdbcLikesDAO)
  {
    this.jdbcLikesDAO = jdbcLikesDAO;
  }
  
  public void setJdbcUserDAO(JdbcUserDAO jdbcUserDAO)  
  {
    this.jdbcUserDAO = jdbcUserDAO;
  }  
}

