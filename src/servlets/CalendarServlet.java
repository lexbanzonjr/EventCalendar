package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import event.Event;
import event.JdbcEventDAO;
import likes.JdbcLikesDAO;
import likes.Likes;
import user.JdbcUserDAO;
import user.User;

@WebServlet(
  name = "CalendarServlet",
  urlPatterns = "/calendar",
  loadOnStartup = 1  
)
public class CalendarServlet extends HttpServlet 
{
  private static JdbcEventDAO jdbcEventDAO;
  private static JdbcLikesDAO jdbcLikesDAO;
  private static JdbcUserDAO jdbcUserDAO;
  
  private static final long serialVersionUID = 1L;
  private EventDataSource eventDataSource = new EventDataSource();
  private UserDataSource userDataSource = new UserDataSource();
  
  public void createTicket(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {    
    String eventName = request.getParameter("eventName");
    String startDateTime = parseDate(request.getParameter("startDateTime"));    
    String endDateTime = parseDate(request.getParameter("endDateTime")); 
    int ownerId = getOwnerId(request);
    
    Event event = new Event(eventName, ownerId, startDateTime, endDateTime);   
    jdbcEventDAO.add(event);
    
    // Go to home page after event has been added
    response.sendRedirect("calendar?action=home");    
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException 
  {  
		User loginUser = getSessionUser(request);
    if(loginUser == null)
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
        long userID = loginUser.getId();
        
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
        long userID = loginUser.getId();
        
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

  public int getOwnerId(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("LoginUserItem");
    return user.getId();  	
  }
  
  public User getSessionUser(HttpServletRequest request)
	{        
	  // Get session
	  HttpSession session = request.getSession();
	  
	  // Check if the user is logged in
	  User loginUser = (User)session.getAttribute("LoginUserItem");
	  return loginUser;  	
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
  
  public String parseDate(String date)
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm-dd-yyyy");
    String pDate = null;
    try
    {
	    Date dDate = simpleDateFormat.parse(date); 
	    pDate = simpleDateFormat.format(dDate); 
    }
    catch (Exception e)
    {
    	System.out.println("Error CalendarServlet.parseDate: " + e.getMessage());
    }
      	
    return pDate;
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
  		//      UserItem user = (UserItem)request.getSession().getAttribute("LoginUserItem");

			User loginUser = getSessionUser(request);
			if(loginUser == null)
      {  
        response.sendRedirect("login");
        return;
      }
		
      List<Event> events = jdbcEventDAO.findAllCreatedByUserId(loginUser.getId()); 
      request.setAttribute("events", events);
      request.getRequestDispatcher("WEB-INF/jsp/view/viewCreatedEvents.jsp").forward(request, response);
    }
  
  public void viewUserFrontPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
  		User loginUser = getSessionUser(request);
      if(loginUser == null)
      {  
        response.sendRedirect("login");
        return;
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
	      Likes like = null;
				try
				{
					like = jdbcLikesDAO.findLike(userId, eventId);
				} 
				catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
	      if (like != null)
	      {
	        event.setLike(true);
	        event.setData("<a href=\"event/dislike/" + eventId + "\">Dislike</a> ");
	      }
	      else
	      {
	        event.setData("<a href=\"event/like/" + eventId + "\">Like</a> ");
	      }
	    }	    
//      List<EventItem> events = eventDataSource.findAllSortedByDate();
//      List<UserItem> users = userDataSource.findAll();
      request.setAttribute("events", eventList);    
      request.getRequestDispatcher("WEB-INF/jsp/view/frontpage.jsp").forward(request, response);
    }  
    
}
