package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import event.Event;
import event.JdbcEventDAO;
import form.CreateNewEventForm;
import likes.JdbcLikesDAO;
import likes.Likes;
import user.JdbcUserDAO;
import user.User;

@Controller
@RequestMapping("event")
public class EventController
{
  private JdbcEventDAO jdbcEventDAO;
  private JdbcUserDAO jdbcUserDAO;
  private JdbcLikesDAO jdbcLikesDAO;
  
  public boolean CheckDateFormatError(String month, String day, String year, boolean required)
  { 
    
    SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");
    boolean error = false;
    
    if (month.isEmpty() && day.isEmpty() && year.isEmpty() && !required )
      return error;

    String dateInString = month + "-" + day + "-" + year;    
    try
    {
      Date date = formatter.parse(dateInString);
    }
    catch (Exception e)
    {
      error = true;
    }
    
    return error;
  }
  
  @RequestMapping(value = "createnewevent", method = RequestMethod.POST)
  public View CreateNewEvent(CreateNewEventForm form, HttpSession session, Map<String, Object> model)
  {    
    String eventName = form.getEventName();    
    
    String startDay = form.getStartDay();
    String startMonth = form.getStartMonth();
    String startYear = form.getStartYear();   
    
    String endDay = form.getEndDay();
    String endMonth = form.getEndMonth();
    String endYear = form.getEndYear();
    
    RedirectView redirect;
    
    if (startMonth.isEmpty() || startDay.isEmpty() || startYear.isEmpty())
    {
      session.setAttribute("error", "Start date cannot be empty.");
      model.put("createNewEventURL", "createnewevent");
      redirect = new RedirectView("/event/{createNewEventURL}", true);
    }
    else if ( CheckDateFormatError(startMonth, startDay, startYear, true) || 
        CheckDateFormatError(endMonth, endDay, endYear, false) )
    {      
      session.setAttribute("error", "Invalid entry for dates.");
      model.put("createNewEventURL", "createnewevent");
      redirect = new RedirectView("/event/{createNewEventURL}", true);
    }
    else
    {        
      User user = (User) session.getAttribute("User");
      int ownerId = user.getId();
      
      String startDateTime = startMonth + "-" + startDay + "-" + startYear;
      String endDateTime = endMonth + "-" + endDay + "-" + endYear;
      Event event = new Event(eventName, ownerId, startDateTime, endDateTime);
      
      jdbcEventDAO.add(event);
      
      model.put("dashboardURL", "dashboard");
      redirect = new RedirectView("/{dashboardURL}", true);
    }
    
    return redirect;
  }
  
  @RequestMapping(value = "createnewevent", method = RequestMethod.GET)
  public String CreateNewEvent(Map<String, Object> model)
  {
    model.put("createNewEventForm", new CreateNewEventForm());
    return "CreateNewEventForm";
  } 
  
  @RequestMapping("dislike/{eventId}")
  public View Dislike(@PathVariable("eventId") int eventId, HttpSession session, Map<String, Object> model)
  {
    // Get user object from session
    User user = (User) session.getAttribute("User");
    
    // Get the id
    int userId = user.getId();
    
    // Add like to Likes table
    Likes likes = new Likes(userId, eventId);
    jdbcLikesDAO.remove(likes);
    
    String currentPage = (String) session.getAttribute("currentPage");
        
    RedirectView redirectView = null;
    if (currentPage.equals("dashboard"))
    {
      model.put("redirectURL", "dashboard");
      redirectView = new RedirectView("../../{redirectURL}", true);
    }
    else if (currentPage.equals("viewCreatedEvents"))
    {
      model.put("redirectURL", "viewcreatedevents");
      redirectView = new RedirectView("../{redirectURL}", true);
    }
    else if (currentPage.equals("viewLikedEvents"))
    {
      model.put("redirectURL", "viewlikedevents");
      redirectView = new RedirectView("../{redirectURL}", true);      
    }

    return redirectView;
  }
    
  @RequestMapping("like/{eventId}")
  public View Like(@PathVariable("eventId") int eventId, HttpSession session, Map<String, Object> model)
  {
    // Get user object from session
    User user = (User) session.getAttribute("User");
    
    // Get the id
    int userId = user.getId();
    
    // Add like to Likes table
    Likes likes = new Likes(userId, eventId);
    jdbcLikesDAO.add(likes);

    String currentPage = (String) session.getAttribute("currentPage");
    
    RedirectView redirectView = null;
    if (currentPage.equals("dashboard"))
    {
      model.put("redirectURL", "dashboard");
      redirectView = new RedirectView("../../{redirectURL}", true);
    }
    else if (currentPage.equals("viewCreatedEvents"))
    {
      model.put("redirectURL", "viewcreatedevents");
      redirectView = new RedirectView("../{redirectURL}", true);
    }

    return redirectView;
  }
  
  @RequestMapping("viewcreatedevents")
  public String ViewCreatedEvents(Map<String, Object> model, HttpSession session) throws Exception 
  {
    User user = (User) session.getAttribute("User");
    int userId = user.getId();
    List<Event> eventList = jdbcEventDAO.findAllCreatedByUserId(userId); 
    for (Event event : eventList)
    {
      // Set owner name in the event   
      int ownerId = event.getOwnerId();
      User ownerUser = jdbcUserDAO.findUserById(ownerId);
      String ownerName = ownerUser.getUsername();
      event.setOwnerName(ownerName);
      
      // Set like in the event
      int eventId = event.getEventId();
      Likes like = jdbcLikesDAO.findLike(ownerId, eventId);
      if (like != null)
      {
        event.setLike(true);
        event.setData("<a href=\"dislike/" + eventId + "\">Dislike</a> ");
      }
      else
      {
        event.setData("<a href=\"like/" + eventId + "\">Like</a> ");
      }
    }
    model.put("eventList", eventList);
    
    return "ViewCreatedEvents";
  }
  
  @RequestMapping("viewlikedevents")
  public String ViewLikeEvents(Map<String, Object> model, HttpSession session) throws Exception
  {  
    User user = (User) session.getAttribute("User");
    int userId = user.getId();
    List<Event> eventList = jdbcEventDAO.findAllEventLikedByUserId(userId); 
    for (Event event : eventList)
    {
      // Set owner name in the event   
      int ownerId = event.getOwnerId();
      User ownerUser = jdbcUserDAO.findUserById(ownerId);
      String ownerName = ownerUser.getUsername();
      event.setOwnerName(ownerName);
      
      // Set like in the event
      int eventId = event.getEventId();
      Likes like = jdbcLikesDAO.findLike(userId, eventId);
      if (like != null)
      {
        event.setLike(true);
        event.setData("<a href=\"dislike/" + eventId + "\">Dislike</a> ");
      }
      else
      {
        event.setData("<a href=\"like/" + eventId + "\">Like</a> ");
      }
    }
    model.put("eventList", eventList);
    
    return "ViewLikedEvents";
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
