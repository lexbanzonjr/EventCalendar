package controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import event.Event;
import event.JpaEventDAO;
import likes.JpaLikesDAO;
import likes.Likes;
import user.JpaUserDAO;
import user.User;

@Controller
public class CalendarController
{  
  
  private JpaEventDAO jpaEventDAO;
  private JpaLikesDAO jpaLikesDAO;
  private JpaUserDAO jpaUserDAO;
  
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
    List<Event> eventList = jpaEventDAO.findAll();      
    for (Event event : eventList)
    { 
      // Set owner name in the event
      int ownerId = event.getOwnerId();
      User user = jpaUserDAO.findUserById(ownerId);
      String ownerName = user.getUsername();
      event.setOwnerName(ownerName);
      
      // Set like in the event
      int eventId = event.getEventId();
      int userId = loginUser.getId();
      Likes like = jpaLikesDAO.findLike(userId, eventId);
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
  
  public void setJpaEventDAO(JpaEventDAO jpaEventDAO)  
  {
    this.jpaEventDAO = jpaEventDAO;
  }
  
  public void setJpaLikesDAO(JpaLikesDAO jpaLikesDAO)
  {
    this.jpaLikesDAO = jpaLikesDAO;
  }
  
  public void setJpaUserDAO(JpaUserDAO jpaUserDAO)  
  {
    this.jpaUserDAO = jpaUserDAO;
  }  
}

