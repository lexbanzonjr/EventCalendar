package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.JdbcUserDAO;
import user.User;

@WebServlet(
    name = "loginServlet",
    urlPatterns = "/login"
)
public class LoginServlet extends HttpServlet
{
  private static JdbcUserDAO jdbcUserDAO;	// Why static?
	private static final long serialVersionUID = 1L;
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException 
  {
    // Create session if not yet created
    HttpSession session = request.getSession();

    //Check if the user is already logged in  
    if (session.getAttribute("LoginUserItem") != null)
    {
    	response.sendRedirect("calendar?action=home");
    	return;
    }
    
    // Check if there is a message
    String message = (String)request.getParameter("message");
    if (message != null)
    {
      switch(message)
      {
        case "badCredentials":
          request.setAttribute("message", "Invalid Username and/or Password.");
          break;
        
        case "CreatedNewUserSuccess":
          request.setAttribute("message", "Created new user successfully.");  
          break;
          
        case "logout":
          request.setAttribute("message", "You have successfully logged out.");
          break;
          
        case "Duplicate":
        	request.setAttribute("message", "User already exists");
        	break;
      }        
    }
    
    // Go to login page if user is not logged in
    request.getRequestDispatcher("WEB-INF/jsp/view/login.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
  	
    User user = null;
    HttpSession session = request.getSession();
		try
    { // Check to see if the user exists in the database
      user = jdbcUserDAO.findUserByUsername(username);
    }
    catch (Exception e)
    { // An exception occurred. The user does not exist.     
      session.setAttribute("error", e.getMessage()); 
    }
    
    // Need to check if the user is not null. A null occurs only if 
    // the exception occurs where the username did not exist in the
    // database.
    if (user != null)
    { 
      // Check the passwords
      if (user.getPassword().equals(password))
      { 
	      session.setAttribute("LoginUserItem", user);      
	      response.sendRedirect("calendar?action=home");
      }
    } 
    else
    {
    	response.sendRedirect("login?message=badCredentials");    	
    }
  }
  
  public void setJdbcUserDAO(JdbcUserDAO jdbcUserDAO)  
  {
    this.jdbcUserDAO = jdbcUserDAO;
  }  
}
