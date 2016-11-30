package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.EventDataSource;
import data.EventItem;
import data.UserDataSource;
import data.UserItem;
import event.JdbcEventDAO;
import likes.JdbcLikesDAO;
import user.JdbcUserDAO;

@WebServlet(
  name = "CalendarServlet",
  urlPatterns = "/calendar",
  loadOnStartup = 1  
)
public class CalendarServlet extends HttpServlet 
{
  private JdbcEventDAO jdbcEventDAO;
  private JdbcLikesDAO jdbcLikesDAO;
  private JdbcUserDAO jdbcUserDAO;
  
  private static final long serialVersionUID = 1L;
  private EventDataSource eventDataSource = new EventDataSource();
  private UserDataSource userDataSource = new UserDataSource();

  public void createTicket(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // Get parameters
    String eventName = request.getParameter("eventName");
    String startDateTime = request.getParameter("startDateTime");
    String endDateTime = request.getParameter("endDateTime");    
    
    // Getting user id    
    UserItem user = (UserItem)request.getSession().getAttribute("LoginUserItem");
    long ownerId = user.getId();
    
    // Create and set properties for event
    EventItem event = EventItem.getNew(ownerId);
    event.setEventName(eventName);
    event.setStartDateTime(startDateTime);
    event.setEndDateTime(endDateTime);
    eventDataSource.add(event);    
    
    // Go to home page after event has been added
    response.sendRedirect("calendar?action=home");
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException 
  {  
    // Create session if not yet created
    HttpSession session = request.getSession();
      
    // Check if the user is logged in
    UserItem loginUserItem = (UserItem)session.getAttribute("LoginUserItem");
    if(loginUserItem == null)
    {  
      response.sendRedirect("login");
      return;
    }      
    
    // Check what action the user is requesting
    String action = request.getParameter("action");
    
    // Always go home if no action
    if (action == null)
      action = "home";
    
    switch(action)
    { // User's home page
      case "home":
        viewUserFrontPage(request, response);
        break;
        
      case "createNewEvent":
        request.getRequestDispatcher("WEB-INF/jsp/view/createNewEvent.jsp").forward(request, response);
        break;
      
      case "dislikeEvent":
      { // local scope
        // Get current user's Id        
        long userID = loginUserItem.getId();
        
        // Get the event Id
        String s = request.getParameter("eventId");
        long eventId = Integer.parseInt(s);        
        
        // Use event id to find EventItem 
        EventItem event = eventDataSource.findEventItemById(eventId);
        
        // Remove the like
        event.removeLikeByUserId(userID);
        break;
      } 
      
      case "likeEvent":
      { // local scope
        // Get current user's Id        
        long userID = loginUserItem.getId();
        
        // Get the event Id
        String s = request.getParameter("eventId");
        long eventId = Integer.parseInt(s);        
        
        // Use event id to find EventItem 
        EventItem event = eventDataSource.findEventItemById(eventId);
        
        // Remove the like
        event.addLikeByUserId(userID);
        break; 
      }        
        
      case "viewCreatedEvents":
        viewCreatedEvents(request, response);
        break;
        
      case "viewLikedEvents":
        viewLikedEvents(request,response);
        break;  
      
      default:
        request.getRequestDispatcher("WEB-INF/jsp/view/default.jsp").forward(request, response);
        break;        
    }  
    
    String redirect = request.getParameter("redirect");
    if (redirect != null)
    {
      switch(redirect)
      {
        case "frontpage":
          viewUserFrontPage(request, response);
          break;
          
        case "viewCreatedEvents":
          viewCreatedEvents(request, response);
          break;
        
        case "viewLikedEvents":
          viewLikedEvents(request,response);
          break;
      }      
    }
  }  
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
      String action = request.getParameter("action");
      if(action == null)
          action = "list";
      switch(action)
      {
          case "createNewEvent":
              this.createTicket(request, response);
              break;
      }
  }

  
  @Override
  public void init() throws ServletException
  {
    /* Events */    
    EventItem event = EventItem.getNew(0);
    event.setEventName("New Years");
    event.setStartDateTime("01-01-2017");
    eventDataSource.add(event);
    event = EventItem.getNew(0);
    event.setEventName("Columbus Day");
    event.setStartDateTime("10-10-2017");
    eventDataSource.add(event);
    event = EventItem.getNew(0);
    event.setEventName("Independence Day");
    event.setStartDateTime("07-04-2017");
    eventDataSource.add(event);
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
  
  public void viewLikedEvents(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    { 
      UserItem user = (UserItem)request.getSession().getAttribute("LoginUserItem");      
      List<EventItem> events = eventDataSource.findAllLikedEventsByUserId(user.getId());
      request.setAttribute("events", events);
      request.getRequestDispatcher("WEB-INF/jsp/view/viewLikedEvents.jsp").forward(request, response);
    }
  
  public void viewCreatedEvents(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    { 
      UserItem user = (UserItem)request.getSession().getAttribute("LoginUserItem");     
      List<EventItem> events = eventDataSource.findAllCreatedByUserId(user.getId());
      request.setAttribute("events", events);
      request.getRequestDispatcher("WEB-INF/jsp/view/viewCreatedEvents.jsp").forward(request, response);
    }
  
  public void viewUserFrontPage(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {        
      List<EventItem> events = eventDataSource.findAllSortedByDate();
      List<UserItem> users = userDataSource.findAll();
      request.setAttribute("events", events);    
      request.setAttribute("users", users);
      request.getRequestDispatcher("WEB-INF/jsp/view/frontpage.jsp").forward(request, response);
    }  
}
