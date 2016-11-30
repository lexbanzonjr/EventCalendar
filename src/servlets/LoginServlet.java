package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.UserDataSource;
import data.UserItem;
import user.JdbcUserDAO;

@WebServlet(
    name = "loginServlet",
    urlPatterns = "/login"
)
public class LoginServlet extends HttpServlet
{
  private JdbcUserDAO jdbcUserDAO;
	private static final long serialVersionUID = 1L;
	private UserDataSource userDataSource = new UserDataSource();
  
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
    
    UserItem userItem = userDataSource.findUserItemByUsername(username);    
  
    if (userItem != null && userItem.getPassword().equals(password))
    {
      UserItem loginUserItem = userItem;
      HttpSession session = request.getSession();
      session.setAttribute("LoginUserItem", loginUserItem);      
      response.sendRedirect("calendar?action=home");
    }
    else
    {
      response.sendRedirect("login?message=badCredentials");
    }
  }
  
  @Override
  public void init() throws ServletException
  {
    /* Users */
    UserItem user = UserItem.getNew("Lex", "test");    
    userDataSource.add(user);
  }  
  
  public void setJdbcUserDAO(JdbcUserDAO jdbcUserDAO)  
  {
    this.jdbcUserDAO = jdbcUserDAO;
  }  
}
